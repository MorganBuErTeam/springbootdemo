package com.test.demo.config;


import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * @ClassName:CxfClient
 * @Description:webservice客户端：
 * 			       该类提供两种不同的方式来调用webservice服务
 * 				1：代理工厂方式
 * 				2：动态调用webservice
 */
public class CxfClient {


//	public static void main(String[] args) {
//		CxfClient.main1();
//	}

	/**
	 * 1.代理类工厂的方式,需要拿到对方的接口地址
	 */
//	public static void main1() {
//		try {
//			// 接口地址
//			String address = "http://localhost:8081/service/wms?wsdl";
//			// 代理工厂
//			JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
//			// 设置代理地址
//			jaxWsProxyFactoryBean.setAddress(address);
//			// 设置接口类型
//			jaxWsProxyFactoryBean.setServiceClass(SmtWebService.class);
//			// 创建一个代理接口实现
//			SmtWebService us = (SmtWebService) jaxWsProxyFactoryBean.create();
//			// 数据准备
//
//			// 调用代理接口的方法调用并返回结果
//			SmtResponseVo objects= us.queryMaterielMasters();
//			System.out.println("返回结果:"+objects.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 2：动态调用
	 */
//	public static void main2() {
//		// 创建动态客户端
//		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//		Client client = dcf.createClient("http://192.168.1.203:8780/soapService/agvWebServices?WSDL");
//		// 需要密码的情况需要加上用户名和密码
////		 client.getOutInterceptors().add(new LoginInterceptor("pls", "123456"));
//
//		try {
//
//			TempAgvStatusModel tempAgvStatusModel=new TempAgvStatusModel();
//			tempAgvStatusModel.setCarNo("1001");
//			tempAgvStatusModel.setTaskNo("101");
//			tempAgvStatusModel.setStatus("4");
//
//			Object[] objects= client.invoke("putAgvStatus",tempAgvStatusModel);
//			System.out.println("返回数据:" + objects[0].toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main3(){
//		TempAgvStatusModel tempAgvStatusModel=new TempAgvStatusModel();
//		tempAgvStatusModel.setCarNo("1001");
//		tempAgvStatusModel.setStatus("4");
//		String strparam = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.pls.ruifu.com/\">\n" +
//				"   <soapenv:Header/>\n" +
//				"   <soapenv:Body>\n" +
//				"      <web:putAgvStatus>\n" +
//				"         <tempAgvStatus>\n" +
//				"            <carNo>"+1+"</carNo>\n" +
//				"            <status>"+2+"</status>\n" +
//				"            <taskNo>"+3+"</taskNo>\n" +
//				"         </tempAgvStatus>\n" +
//				"      </web:putAgvStatus>\n" +
//				"   </soapenv:Body>\n" +
//				"</soapenv:Envelope>";
//		String result = HttpUtility.post(strparam, "http://192.168.1.203:8780/soapService/agvWebServices?WSDL");
//		System.out.println(result);
////		String result = HttpUtility.post(strparam, "http://79sikr.natappfree.cc/soapService/agvWebService/tempAgvStatus?wsdl");
////		String messageOne=result.substring(result.indexOf("message")+8);
////		String message=messageOne.substring(0,messageOne.lastIndexOf("</message>"));
////		String successOne=result.substring(result.indexOf("success")+8);
////		String success=successOne.substring(0,successOne.lastIndexOf("</success>"));
////		WebServiceResult webServiceResult=new WebServiceResult();
////		webServiceResult.setMessage(message);
////		webServiceResult.setSuccess(Boolean.parseBoolean(success));
////		System.out.println(result);
//	}

	public static void main4(){
		//解析wms返回信息
		String result="<return><message>000#同步状态成功</message><success>true</success></return>";
		String messageOne=result.substring(result.indexOf("message")+8);
		String message=messageOne.substring(0,messageOne.lastIndexOf("</message>"));
		String successOne=result.substring(result.indexOf("success")+8);
		String success=successOne.substring(0,successOne.lastIndexOf("</success>"));
		System.out.println(message);
	}

}
