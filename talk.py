import socket

import info

HOST = '127.0.0.1'  # 监听的地址
PORT = 8888  # 监听的端口号

while True:
    try:

        # 创建套接字并监听
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server_socket.bind((HOST, PORT))
        server_socket.listen(1)

        # 等待客户端连接
        print('Waiting for Java client...')
        client_socket, address = server_socket.accept()
        print('Connected by', address)
        client_socket.settimeout(0.5)
        # 接收Java客户端发送的数据并返回
        result = ""
        while True:
            data = client_socket.recv(1024)
            if len(data) < 1024:
                break
            print('Received from Java client:', data.decode())
            result += data.decode()

        result1 = info.fun(result)
        client_socket.sendall(result1.encode())
        print(2)
        # 关闭套接字
        client_socket.close()
        server_socket.close()
    except Exception as err:
        continue
