#define  _POSIX_C_SOURCE 200809L
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/epoll.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <assert.h>

#include <sys/socket.h>
#include <arpa/inet.h>

#define exit(N) {fflush(stdout); fflush(stderr); _exit(N); }
#define BMAX 4096

static int get_port(void);
static int Socket(int namespace, int style, int protocol);
static void Bind(int sockfd, struct sockaddr * server, socklen_t length);
static void Listen(int sockfd, int qlen);
static int Accept(int sockfd, struct sockaddr * addr, socklen_t * length_ptr);
static void clear_buffers();

static char request[BMAX+1];
static ssize_t request_size = 0;

static char response[BMAX+1];
static int response_size = 0;

static char header_response[BMAX+1];
static int header_size = 0;

static char body_response[BMAX+1];
static int body_size = 0;

static int good_requests = 0;
static int header_bytes = 0;
static int body_bytes = 0;
static int num_errors = 0;
static int error_bytes = 0;


static int accept_client(int listenfd)
{
    static struct sockaddr_in client;
    static socklen_t csize = 0;
    memset(&client, 0x00, sizeof(client));
    int clientfd = Accept(listenfd, (struct sockaddr*)&client, &csize);
    return clientfd;
}


static int ping()
{
    //
    strcpy(header_response, "Content-Length: 4\r\n\r\n");
    header_size = strlen(header_response);
    strcpy(body_response, "pong"); 
    body_size = strlen(body_response);
    return 0;
}


static int echo()
{
    char * body = strstr(request, "\r\n");
    body += 2; //move past CRLF character
    
    char * end = strstr(body, "\r\n\r\n");
    
    //if the header section is greater then 1024
    //bytes, send a 413 error
    if ((end - body) > 1024) {
        return 3;
    }

    strncpy(body_response, body, end - body);
    //body_response[BMAX] = '\0';

    body_size = (end - body);

    char header[BMAX];
    sprintf(header, "Content-Length: %d%s", body_size, "\r\n\r\n");
    
    strcpy(header_response, header);
    //header_response[BMAX] = '\0';
    header_size = strlen(header_response);
    return 0;
}


static int server_read()
{
    // 
    if (response_size == 0) {
        strcpy(header_response, "Content-Length: 7\r\n\r\n");
	strcpy(body_response, "<empty>");
	body_size = strlen(body_response);
    }
    return 0;
}


static int server_write()
{
    //
    char * ptr = strstr(request, "Content-Length"); //find the header Content-Length
    char * content_length = strstr(ptr, " ");
    content_length++;
    size_t length = atoi(content_length); //get the length into an integer
    
    //if the POST request attempts to post more then
    //1024 bytes, send a 413 error
    //if (length > 1024) {
    //    return 3;
    //} 

    char header[BMAX];
    sprintf(header, "Content-Length: %ld%s", length, "\r\n\r\n");
    header_size = strlen(header_response);

    strcpy(header_response, header);

    char * body = strstr(request, "\r\n\r\n");
    body += 4; //move past the 2 CRLF's 
    
    memcpy(body_response, body, length);
    body_size = length;
    return 0;
}


static size_t post_length() {
    char * ptr = strstr(request, "Content-Length"); //find the header Content-Length
    char * content_length = strstr(ptr, " ");
    
    content_length++;
    size_t length = atoi(content_length); //get the length into an integer
	
    return length;
}


static int check_file(int clientfd)
{
    //
    char * ptr = strstr(request, "/");
    ptr++;

    while (*ptr == '/') {
        ptr++;
    }

    char * end = strstr(ptr, " ");
    char filename[(end-ptr)+1];
    memset(filename, 0x00, sizeof(filename));
    memcpy(filename, ptr, end-ptr);
    filename[end-ptr] = '\0';

    int fd = open(filename, O_RDONLY, 0644);
    if (fd < 0) {
        //file does not exist, so send error 404
	return 1;
    }
    memset(filename, 0x00, sizeof(filename));

    struct stat buf;
    fstat(fd, &buf);
    
    if (S_ISREG(buf.st_mode) == 0) {
        //file is not a regular file, so send error 404
	return 1;
    }
    
    char header[BMAX];
    sprintf(header, "Content-Length: %lu%s", buf.st_size, "\r\n\r\n");
    
    strcpy(header_response, header);
    header_size = strlen(header_response);

    strcpy(response, "HTTP/1.1 200 OK\r\n");
    strcat (response, header_response);
    response_size = strlen(response);

    good_requests++;
    header_bytes += response_size;

    send(clientfd, response, strlen(response), 0);

    ssize_t amt = 0;
    ssize_t sent = 0;
    //char buffer[1024];
    while (sent < buf.st_size) {
        amt = read(fd, body_response, 1024);
        sent += send(clientfd, body_response, amt, 0);
        memset(body_response, 0x00, sizeof(body_response));	
    }
    close(fd);
    body_size = buf.st_size;
   
    body_bytes += body_size;

    clear_buffers();
    return 4;

}


static int get_stats()
{
    //
    char body[BMAX];
    sprintf(body, "Requests: %d\nHeader bytes: %d\nBody bytes: %d\nErrors: %d\nError bytes: %d",
		    good_requests, header_bytes, body_bytes, num_errors, error_bytes);

    strcpy(body_response, body);
    body_size = strlen(body_response);

    char header[BMAX];
    sprintf(header, "Content-Length: %d%s", body_size, "\r\n\r\n");

    strcpy(header_response, header);
    header_size = strlen(header_response);
    
    return 0;
}


static void clear_buffers()
{
    //memset(request, 0x00, sizeof(request));
    //request_size = 0;

    memset(response, 0x00, sizeof(response));
    response_size = 0;

    memset(header_response, 0x00, sizeof(header_response));
    header_size = 0;

    memset(body_response, 0x00, sizeof(body_response));
    body_size = 0;
}


static int validate_request()
{
    //
    char * ptr = NULL;
    int method_len = 0;
    if ((ptr = strstr(request, "GET")) != NULL) {
        method_len = 3;
    }
    else if ((ptr = strstr(request, "POST")) != NULL) {
        method_len = 4;
    } 
    //if method is not GET or POST, then it is
    //a bad request that this server can not handle
    else {
        return -1;
    }

    ptr += method_len;
    //if the next character after the method is not a 
    //space, the form of the request is invalid
    if (*ptr != ' ') {
        return -1;
    }

    if ((ptr = strstr(request, "HTTP/1.1\r\n")) != NULL) {
        ptr--;
        
	//there needs to be a space after the URL and before
	//the HTTP/1.1\r\n, if there is not the form is invalid
	if (*ptr != ' ') {
	    return -1;
	}
    }
    //request needs to have HTTP/1.1 followed by a CRLF,
    //so if it is not there it is an invalid request 
    else {
        return -1;
    }
	
    //request must have two CRLF's to denote the end of the header,
    //if this is not found then the form of the request is invalid
    if (NULL == strstr(request, "\r\n\r\n")) {
        return -1;
    }

    return 0;
}


static void send_404_error(int clientfd)
{
    //
    strcpy(response, "HTTP/1.1 404 Not Found\r\n\r\n");
    response_size = strlen(response);

    num_errors++;
    error_bytes += response_size;

    int amount = send(clientfd, response, response_size, 0);
    clear_buffers();
}


static void send_400_error(int clientfd)
{
    //
    strcpy(response, "HTTP/1.1 400 Bad Request\r\n\r\n");
    response_size = strlen(response);

    num_errors++;
    error_bytes += response_size;

    int amount = send(clientfd, response, response_size, 0);
    clear_buffers();
}


static void send_413_error(int clientfd)
{
    strcpy(response, "HTTP/1.1 413 Request Entity Too Large\r\n\r\n");
    response_size = strlen(response);

    num_errors++;
    error_bytes += response_size;

    int amount = send(clientfd, response, response_size, 0);
    if ((header_size != 0) || (body_size != 0)) {
        return;
    }
    clear_buffers();
}


static void send_response(int clientfd) 
{
    //
    strcpy(response, "HTTP/1.1 200 OK\r\n");
    strcat(response, header_response);
    response_size = strlen(response);

    good_requests++;
    header_bytes += response_size;

    memcpy(&response[response_size], body_response, body_size);
    response_size += body_size;

    body_bytes += body_size;

    int amount = send(clientfd, response, response_size, 0);
    while (amount < response_size) {
	amount += send(clientfd, response, response_size, 0);
    }

    //empty all buffers if request wasn't POST
    if ((strstr(request, "POST")) || (strstr(request, "/read"))) {
        //clear_buffers();
        return;
    }
    clear_buffers();
}


static void handle_request(int clientfd) 
{
    request_size = recv(clientfd, request, BMAX, 0);
    request[request_size] = '\0';

    if (request_size == 0) {
        return;
    }

    //
    int code = -1;
    int check_req = validate_request();
    if (check_req == -1) {
        //request is invalid, so send 400 error
	send_400_error(clientfd);
	return;
    }

    if (strstr(request, "GET")) { //request is a GET request
        //
	//see if request is for /ping
        if (strstr(request, "/ping")) {
	    code = ping();
        }
	//see if request is for /echo
	else if (strstr(request, "/echo")) {
	    code = echo();

	}
	//see if request is for /read
	else if (strstr(request, "/read")) {
            code = server_read();

        }
	else if (strstr(request, "/stats")) {
	    code = get_stats();
	}
	//send 400 error if request is  GET /write, as this is 
	//and invalid combo of METHOD and URL
	//else if (strstr(request, "/write")) {
	//    code = 2;
	//}
	else {
	    //
	    code = check_file(clientfd);
	}
        //
        //send_response(clientfd);
    }
    else if (strstr(request, "POST")) { //request is a POST resquest
        //
	//size_t post_len = post_length();
        
	//if the POST request attempts to post more then
        //1024 bytes, send a 413 error
	//if (post_len > 1024) {
	//    code = 3;:wq
	//    
	//}
	
	if (strstr(request, "/write")) {
	    size_t post_len = post_length();

            //if the POST request attempts to post more then
            //1024 bytes, send a 413 error
            if (post_len > 1024) {
                code = 3;
            }
	    else {
		clear_buffers();
	        code = server_write();
	    }
	    
	}
	else { //if POST is not followed by /write, send a 400 error
            code = 2;
        }

        //else {
	    //clear_buffers();
	    //if (strstr(request, "/write")) {
	    //    code = server_write();
	    //}
	    //else { //if POST is not followed by /write, send a 400 error
	    //    code = 2;
	    //}
	//}
	//
	//send_response(clientfd);
    }
    else {
        //request did not follow proper format, so send
	//a 400 error
	code = 2;
    }

    if (code == 0) {
        send_response(clientfd);
    } 
    if (code == 1) {
        //send 404 error
	send_404_error(clientfd);
    } 
    if (code == 2) {
        //send 400 error
	send_400_error(clientfd);
    } 
    if (code == 3) {
        //send 413 error
	send_413_error(clientfd);
    } else {
        //send nothing
	return;
    }

}


int main(int argc, char * argv[])
{
    int port = get_port();

    printf("Using port %d\n", port);
    printf("PID: %d\n", getpid());

    // Make server available on port
    int listenfd = Socket(AF_INET, SOCK_STREAM, 0);
    
    static struct sockaddr_in server;
    server.sin_family = AF_INET;
    server.sin_port = htons(port);
    inet_pton(AF_INET, "127.0.0.1", &server.sin_addr);

    int optval = 1;
    setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &optval, sizeof(optval));

    Bind(listenfd, (struct sockaddr*)&server, sizeof(server));
    Listen(listenfd, 10);

    // Process client requests
    while (1) {
       int clientfd = accept_client(listenfd);
       //process the client transaction
       handle_request(clientfd);
       //close connection
       close(clientfd);
    }

    return 0;
}


static int Socket(int namespace, int style, int protocol) 
{
    int sockfd = socket(namespace, style, protocol);
    if (sockfd < 0) {
	perror("socket");
	exit(1);
    }
    return sockfd;
}


static void Bind(int sockfd, struct sockaddr * server, socklen_t length)
{
    if (bind(sockfd, server, length) < 0) {
        perror("bind");
	exit(1);
    }
}


static void Listen(int sockfd, int qlen) 
{
    if (listen(sockfd, qlen) < 0) {
        perror("listen");
	exit(1);
    }
}


static int Accept(int sockfd, struct sockaddr * addr, socklen_t * length_ptr)
{
    int newfd = accept(sockfd, addr, length_ptr);
    if (newfd < 0) {
        perror("accept");
	exit(1);
    }
    return newfd;
}


static int get_port(void)
{
    int fd = open("port.txt", O_RDONLY);
    if (fd < 0) {
        perror("Could not open port.txt");
        exit(1);
    }

    char buffer[32];
    int r = read(fd, buffer, sizeof(buffer));
    if (r < 0) {
        perror("Could not read port.txt");
        exit(1);
    }

    return atoi(buffer);
}
