# Mongoose


A  red-team application with a console menu.

### Start Project
You have the option to either use the JAR provided (in the root of this folder) or construct the project using maven.

It is important to use this application in accordance with your local regulations and laws.

#### The Red team app has 5 options to choose from!



    1. Launch an application by selecting the option number from the console menu.

    2. To exit an application and return to the console menu, use enter twice.


## Menu Reference

#### 1. SIP null byte

```
  Sends a null byte to a server running SIP.
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `arg[0]` | `string` | **Required**.  URL of the target (including protocol)   |
| `arg[1]` | `string` | **Required**.  Use default SIP port or set a custom one   |

####  2. Node JS Fuzzer

```
  This is effective options against servers running Node Js ;)
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `arg[0]`      | `string` | **Required**. URL of the target (including protocol and port)
| `arg[1]` | `string` | **Required**.  Capacity of the Stringbuilder that build the fuzzing data   |

```The bigger the fuzzing size, the better it is for fuzzing them away, but if you want to do buffer overflow, there are better options available.```

#### 3. Firewalk


```
  1. Traceroute to intended target 
  2. Use this FireWalk option
  3. Traceroute again when you have passed the gatekeeper
  4. Repeat steps
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `arg[0]` | `string` | **Required**.  IP of the target   |
| `arg[1]` | `string` | **Required**.  Start port   |
| `arg[2]` | `string` | **Required**.  End port   |


Note that this is the only option that uses IP instead of DNS

#### 4. Credential crawler

```
  Crawl for key value pairs on a given url and add optional child paths
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `arg[0]` | `string` | **Required**.  URL of the target (including protocol)   |
| `arg[3] and more....` | `string` | **optional**.  Add custom child paths   |


#### 5. Picture changer

```
  Under construction....
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `arg[0]` | `string` | **Required**. Remote URL to the path of a picture    |
| `arg[1]` | `string` | **Required**. Local file path to a image file  |

## Screenshots

![App Screenshot](https://cosmosmagazine.com/wp-content/uploads/2019/12/190620_mongoose_pr.jpg)
