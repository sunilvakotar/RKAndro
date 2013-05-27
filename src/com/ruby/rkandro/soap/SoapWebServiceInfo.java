package com.ruby.rkandro.soap;

public class SoapWebServiceInfo {
	
	//public static final String RESULT = "Result";
	
	public static final String URL = "http://rkandro.com/WebService.asmx";
												
	public static final String RKLIST_ENVELOPE ="<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetProject_Data xmlns=\"http://tempuri.org/\" /></soap:Body></soap:Envelope>";
	public static final String RKLIST_SOAP_ACTION = "http://tempuri.org/GetProject_Data";
	public static final String RKLIST_RESULT_TAG = "GetProject_DataResult";

    public static final String GETLIST_ENVELOPE ="<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetList xmlns=\"http://tempuri.org/\" /></soap:Body></soap:Envelope>";
    public static final String GETLIST_SOAP_ACTION = "http://tempuri.org/GetList";
    public static final String GETLIST_RESULT_TAG = "GetListResult";

    public static final String GETDETAIL_ENVELOPE ="<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetDetial xmlns=\"http://tempuri.org/\"><id>%s</id></GetDetial></soap:Body></soap:Envelope>";
    public static final String GETDETAIL_SOAP_ACTION = "http://tempuri.org/GetDetial";
    public static final String GETDETAIL_RESULT_TAG = "GetDetialResult";
	
	
}
