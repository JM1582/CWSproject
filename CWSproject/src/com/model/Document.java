package com.model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Document{
	//attributes
	private int documentId=-1;
	private FormTemplate formTemplate;
	private int version;
	private int serialNumber;
	private String documentName;
	private String description;
	private String date;
	private CareProvider author;
	private String CWSNumber;
	private boolean sign;
	private Map domainValueMap;

	public Document(FormTemplate formTemplate, CareProvider author, String CWSNumber){
		this.formTemplate = formTemplate;
		this.author = author;
		this.CWSNumber = CWSNumber;
		this.domainValueMap = new HashMap();
	}

	public FormTemplate getFormTemplate(){
		return this.formTemplate;
	}
	public void setFormTemplate(FormTemplate formTemplate){
		this.formTemplate = formTemplate;
	}
	
	public int getSerialNumber(){
		return this.serialNumber;
	}
	public void setSerialNumber(int serialNumber){
		this.serialNumber = serialNumber;
	}

	public int getVersion(){
		return this.version;
	}
	public void setVersion(int newVersion){
		this.version = newVersion;
	}
	//name;Name
	public String getDocumentName(){
		return this.documentName;
	}
	public void setDocumentName(String newName){
		this.documentName = newName;
	}
	//CWSNumber
	public String getCWSNumber(){
		return this.CWSNumber;
	}
	public void setCWSNumber(String CWSNumber){
		this.CWSNumber = CWSNumber;
	}
	//date;newDate
	public String getDate(){
		return this.date;
	}
	public void setDate(String newDate){
		this.date = newDate;
	}
	public void setDateToday(){
		//Month need to be changed!!!!
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		this.date = (String)sdf.format(date);
	}
	public boolean laterThan(Document document){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		try {
			Date dateFromThis = sdf.parse(this.getDate());
			Date dateFormParam = sdf.parse(document.getDate());
			if(dateFromThis.after(dateFormParam)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	public String getDateOnly(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(this.date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("dd/mm/yyyy");
		return (String)sdf.format(date);
	}
	
	//author;Author
	public CareProvider getAuthor(){
		return this.author;
	}
	public void setAuthor(CareProvider newAuthor){
		this.author = newAuthor;
	}
	//document id
	public int getDocumentId() {
		return this.documentId;
	}
	public void setDocumentId(int documentId){
		this.documentId = documentId;
	}
	//valueMap
	public Map getDomainValueMap() {
		return this.domainValueMap;
	}
	public void setDomainValueMap(Map domainValueMap){
		this.domainValueMap = domainValueMap;
	}
	public void addDomainValue(String domainId, String domainValue[]){
		this.domainValueMap.put(domainId, domainValue);
	}
	public void removeDomainValue(String domainId){
		this.domainValueMap.remove(domainId);
	}
	public boolean getSign(){
		return this.sign;
	}
	public void setSign(boolean sign){
		this.sign = sign;
	}
}
