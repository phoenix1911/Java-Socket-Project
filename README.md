��Ŀ�ܽ�
	* 1.��Ŀ�ļ��ṹ��

		* 
	* ��Ŀ����

		* ����

			* 1.�������ض��˿ڵķ������׽����ࣨServerSocket������
			* 2.ѭ�����������յ����׽��ֵ����ӣ�serversocket.accept() ,�������մ��׽��� socket �ķ������̶߳��󣬲������÷������̶߳���


	* �������߳���

		* 0.ʵ�� Ruanable �ӿ���д run() ����
		* 1.���� HttpRequest ���󣬴��� socket
		* 2.���� HttpRequest ����� getRequestInfo() ������ȡ�����С�����ͷ����������Ϣ
		* 3.���� HttpResponse ���󣬴�������� HttpRequset ����� socket
		* 4.���� HttpResponse ����� sendResponse() ��������Ӧ��Ϣ���͸������
		* 5.�ж� socket �Ƿ�Ϊ�� �����Ϊ�չر��׽���


	* HttpRequest ��

		* getRequestInfo��������

			* 1.���û�ȡ������ getRequestLine() ����

				* ���� BufferdReader ����� readline() ��������ȡ��������͵ĵ�һ������
				* ���ո�split(" ")����Ϊ�����ַ��� �ֱ�Ϊ���󷽷���������Դ·����http�汾�Ų������ڶ�Ӧ�ĳ�Ա������
				* �ж�������Դ·���Ƿ���� "?" ��string.indexOf("?") != -1��,���������˵���������ͨ�� GET ��ʽ�ύ����Ϣ��/loginServlet?name=zs&pwd=123������������Դ·���ַ����� "?" "&" "="�ָ��

					* 1."?"֮ǰ���ַ�������Ϊ��Դ·��
					* 2."?"֮����ַ�������Ϊ name zs ,pwd 123 ������ Map<String,String>�����С�

               
	* 2.���û�ȡ����ͷ getRequestHead() ����

		* �� BufferedRead.readLine() ������ȡ�ĵ��ַ�����Ϊ "" ʱ

			* ��ÿһ���ַ����� ": " �ָ�������ַ���������
			* �����������ַ����� Key-Value ��ʽ����������ͷ Map<String,String> ������


	* 3.���û�ȡ������ getRequestBody() ����

		* �ж� ���󷽷� ����� GET ���� ����������   
		* ���󷽷� ����� POST ִ��

			* ���� char[]����
			* ʹ�� BufferedReader ��� read(char[], int off, int len) ���� �������ַ������ַ�����
			* ���ַ�����ת�����ַ�����name=zs&pwd=123��
			* ���ַ�����"&" "="�ָ������Ϊ name zs ,pwd 123 ������ Map<String,String>�����С�


	* ��������ͷ��������Դ·����http�汾�š�����ͷ���ϡ�������ύ��Ϣ ��Ա������get����


	* HttpResponse��

		* sendResponse() ����

			* �ж�������ʲô��Դ������Դ·����"/"��ȡ��resourceFile.substring(1);�������ö�̬��Դ������� getValue() �����������ļ��Ƿ�����õ���¼

				* ��̬��Դ���������˵��������̬��Դ

					* ���÷��� Class<?> class1 =Class.forName(�����ļ���ȡ��ȫ����) �õ��ֽ������
					* ���÷��䷽�� getDeclaredMethod(String name, Class<?>... parameterTypes) ��ȡһ��Method����  Method method = class1.getDeclaredMethod("service",HttpRequest.class,HttpResponse.class);
					* ������ Class ��������ʾ�����һ����ʵ����Object newInstance = class1.newInstance();
					* ���� servlet �� service() ���� ͨ��Method�����invoke������invoke(Object obj, Object... args)�Դ���ָ��������ָ����������ɴ� Method �����ʾ�ĵײ㷽����method.invoke(newInstance,request, this);
					* return ��������
				* ��̬��Դ����������ļ�������˵���Ǿ�̬��Դ

					* �������Դ·��ʵ�����ļ����� new  File(��Դ·��)
					* �ж���Դ·���Ƿ��� "/"(resourceFile.equals("/"))

						* �����

							* ��״̬�������ļ��õ� "200"��value ������Ա����statemsg
							* ʵ�����ļ��ַ��� FileReader ���� ����Ӿ�̬�����ļ��õ��Ļ�ӭҳ��·���ļ�����
						* ������� �ж� ����Դ·���ļ��Ƿ����

							* �������

								* ��״̬�������ļ��õ� "200"��value ������Ա����statemsg
								* ʵ�����ļ��ַ��� FileReader ���� ������Դ·���ļ�����   
							* ���������

								* ��״̬�������ļ��õ� "400"��value ������Ա����statemsg
								* ʵ�����ļ��ַ��� FileReader ���� ������Դ·���ļ��õ���404ҳ��·���ļ�����
					* ���÷��ͷ��� send() �����ļ��ַ���

		* send() ����

			* �Ӳ�����ȡ�ļ��ַ���
			* ��ȡ���������ַ�
			* �����������printStream���� println() �����������������Ӧ��Ϣ

				* ��Ӧ�У�http�汾��״̬��Ϣ��
				* ��Ӧͷ
				* ��Ӧ���У���
				* ��Ӧ�壨��ȡ����������Ϣ��


	* setResourceFile() ��Դ·��������set����


	* LoginServlet��
	* service��HttpRequest request, HttpResponse response������

		* ���� requst ����� getRequestBodyMap()��������ȡ��������ύ�ı���ϢMap��
		* ���� map �� get() �����ȶ� name �� pwd �� value �Ƿ���ȷ

			* ��ȷ������response��setResourceFile() ���� ����Դ·����Ϊ��success.html��
			* ����ȷ������response��setResourceFile() ���� ����Դ·����Ϊ��fail.html��
		* ����response �ķ�����Ӧ���� sendResponse();


	* �����ļ�������

		* ��̬�����

			* ʵ����Properties����� properties
			* ����(properties.load())��ǰ�ļ�·���µ���Դ�ļ�
			* 
			* properties.load(BasicConfigToolProperties.class.getResourceAsStream("basicConfigTool.properties"));
		* getValue(String key������

			* ������Դ�ļ���key ��valueֵproperties.getProperty(key);


	* ��Ŀ��ͼ

