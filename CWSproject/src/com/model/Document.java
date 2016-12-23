package com.model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Document{
	//attributes
	private int documentId=-1;
	private int serialNumber;
	private FormTemplate formTemplate;
	private String documentName;
	private int version;
	private String description;
	private String date;
	private CareProvider author;
	private String CWSNumber;
	private boolean sign=false;
	private Map<String, String[]> domainValueMap;

	public Document(FormTemplate formTemplate, CareProvider author, String CWSNumber){
		this.formTemplate = formTemplate;
		this.author = author;
		this.CWSNumber = CWSNumber;
		this.domainValueMap = new HashMap<String, String[]>();
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
	public String getName(){
		return this.documentName;
	}
	public void setName(String newName){
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = new Date();
		this.date = (String)sdf.format(date);
	}
	public boolean laterThan(Document document) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
		try {
			Date dateFromThis = sdf.parse(this.getDate());
			Date dateFormParam = sdf.parse(document.getDate());
			if(dateFromThis.after(dateFormParam)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}
	public String getDateOnly() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = null;
		try {
			date = sdf.parse(this.date);
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}
		sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
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
	public int getId() {
		return this.documentId;
	}
	public void setId(int documentId){
		this.documentId = documentId;
	}
	//valueMap
	public Map<String, String[]> getDomainValueMap() {
		return this.domainValueMap;
	}
	public void setDomainValueMap(Map<String, String[]> domainValueMap){
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
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}

	public void getUnsetDomainValueFrom(Document document) {
		Iterator<String> domainValueIt = document.domainValueMap.keySet().iterator();
		while(domainValueIt.hasNext()){
			String domainId = (String) domainValueIt.next();
			String domainValue[] = document.getDomainValueMap().get(domainId);
			if(!this.domainValueMap.containsKey(domainId)){
				this.addDomainValue(domainId, domainValue);
			} else {
				String thisDomainValue[] = this.domainValueMap.get(domainId);
				for(int i=0;i<thisDomainValue.length;i++){
					if(domainValue[i]!=null && !domainValue[i].equals("")){
						if(thisDomainValue[i]==null || thisDomainValue[i].equals("")){
							thisDomainValue[i] = domainValue[i];
						}
					}
				}
				this.addDomainValue(domainId, thisDomainValue);
			}
		}
		
	}
}
