项目总结
	* 1.项目文件结构。

		* 
	* 项目流程

		* 主类

			* 1.创建绑定特定端口的服务器套接字类（ServerSocket）对象
			* 2.循环侦听并接收到此套接字的连接（serversocket.accept() ,创建接收此套接字 socket 的服务器线程对象，并启动该服务器线程对象。


	* 服务器线程类

		* 0.实现 Ruanable 接口重写 run() 方法
		* 1.创建 HttpRequest 对象，传入 socket
		* 2.调用 HttpRequest 对象的 getRequestInfo() 方法获取请求行、请求头、请求体信息
		* 3.创建 HttpResponse 对象，传入上面的 HttpRequset 对象和 socket
		* 4.调用 HttpResponse 对象的 sendResponse() 方法将响应信息发送给浏览器
		* 5.判断 socket 是否为空 如果不为空关闭套接字


	* HttpRequest 类

		* getRequestInfo（）方法

			* 1.调用获取请求行 getRequestLine() 方法

				* 调用 BufferdReader 对象的 readline() 方法，读取浏览器发送的第一行数据
				* 按空格（split(" ")）分为三条字符串 分别为请求方法、请求资源路径，http版本号并保存在对应的成员变量里
				* 判断请求资源路径是否包含 "?" （string.indexOf("?") != -1）,如果包含，说明是浏览器通过 GET 方式提交的信息（/loginServlet?name=zs&pwd=123）。将请求资源路径字符串按 "?" "&" "="分割开：

					* 1."?"之前的字符串保存为资源路径
					* 2."?"之后的字符串处理为 name zs ,pwd 123 保存在 Map<String,String>集合中。

               
	* 2.调用获取请求头 getRequestHead() 方法

		* 当 BufferedRead.readLine() 方法读取的的字符串不为 "" 时

			* 将每一行字符串从 ": " 分割开保存在字符串数组里
			* 将左右两个字符串按 Key-Value 形式保存在请求头 Map<String,String> 集合中


	* 3.调用获取请求体 getRequestBody() 方法

		* 判断 请求方法 如果是 GET 方法 跳出方法。   
		* 请求方法 如果是 POST 执行

			* 创建 char[]数组
			* 使用 BufferedReader 类的 read(char[], int off, int len) 方法 将该流字符读入字符数组
			* 将字符数组转换成字符串（name=zs&pwd=123）
			* 将字符串按"&" "="分割开，处理为 name zs ,pwd 123 保存在 Map<String,String>集合中。


	* 生成请求头、请求资源路径、http版本号、请求头集合、浏览器提交信息 成员变量的get方法


	* HttpResponse类

		* sendResponse() 方法

			* 判断请求是什么资源，将资源路径从"/"截取（resourceFile.substring(1);），调用动态资源工具类的 getValue() 方法看配置文件是否包含该调记录

				* 动态资源：如果包含说明是请求动态资源

					* 利用反射 Class<?> class1 =Class.forName(配置文件获取的全包名) 得到字节码对象。
					* 利用反射方法 getDeclaredMethod(String name, Class<?>... parameterTypes) 获取一个Method对象  Method method = class1.getDeclaredMethod("service",HttpRequest.class,HttpResponse.class);
					* 创建此 Class 对象所表示的类的一个新实例。Object newInstance = class1.newInstance();
					* 调用 servlet 的 service() 方法 通过Method的类的invoke方法（invoke(Object obj, Object... args)对带有指定参数的指定对象调用由此 Method 对象表示的底层方法）method.invoke(newInstance,request, this);
					* return 跳出方法
				* 静态资源：如果配置文件不包含说明是静态资源

					* 传入的资源路径实例化文件对象 new  File(资源路径)
					* 判断资源路径是否是 "/"(resourceFile.equals("/"))

						* 如果是

							* 从状态码配置文件得到 "200"的value 传给成员变量statemsg
							* 实例化文件字符流 FileReader 对象 传入从静态配置文件得到的欢迎页面路径文件对象
						* 如果不是 判断 该资源路径文件是否存在

							* 如果存在

								* 从状态码配置文件得到 "200"的value 传给成员变量statemsg
								* 实例化文件字符流 FileReader 对象 传入资源路径文件对象   
							* 如果不存在

								* 从状态码配置文件得到 "400"的value 传给成员变量statemsg
								* 实例化文件字符流 FileReader 对象 传入资源路径文件得到的404页面路径文件对象
					* 调用发送方法 send() 传参文件字符流

		* send() 方法

			* 从参数获取文件字符流
			* 读取该流所有字符
			* 调用输出流（printStream）的 println() 方法向浏览器返回响应信息

				* 响应行（http版本，状态信息）
				* 响应头
				* 响应空行（）
				* 响应体（读取到的所有信息）


	* setResourceFile() 资源路径变量的set方法


	* LoginServlet类
	* service（HttpRequest request, HttpResponse response）方法

		* 调用 requst 对象的 getRequestBodyMap()方法，获取从浏览器提交的表单信息Map。
		* 调用 map 的 get() 方法比对 name 和 pwd 的 value 是否正确

			* 正确：调用response的setResourceFile() 方法 将资源路径设为“success.html”
			* 不正确：调用response的setResourceFile() 方法 将资源路径设为“fail.html”
		* 调用response 的发送响应方法 sendResponse();


	* 配置文件工具类

		* 静态代码块

			* 实例化Properties类对象 properties
			* 加载(properties.load())当前文件路径下的资源文件
			* 
			* properties.load(BasicConfigToolProperties.class.getResourceAsStream("basicConfigTool.properties"));
		* getValue(String key）方法

			* 返回资源文件里key 的value值properties.getProperty(key);


	* 项目截图

