CS9053 Homework8
Congyue Zhang 0486449

[How to compile]
cd Server
javac ChatRoomServer.java

cd Client
javac ChatClient.java

[Run with default arguments]
1.java ChatRoomServer
2.java ChatClient

[Run with command-line arguments]
1.java ChatRoomServer <port>
2.java ChatClient <host> <port> <username>
3.<repeat 2 to create more client> 

[Note]
1.Client hasn't changed since last assignment.
2.Server will print out useful debug information on console.
3.Server encapsulates several exclusive inner classes in order to reduce complexity.