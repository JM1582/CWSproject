function expand(objid){
	var obj=document.getElementById(objid);
	if(obj.style.display != "table"){
		obj.style.display="table";
	}else{
		obj.style.display="none";
	}
}

function collapse(objid){
	var obj=document.getElementById(objid);
	if(obj.style.display != "table"){
		obj.style.display="table";
	}else{
		obj.style.display="none";
	}
}

function active_nav(objid){
	elementDisplay(objid);
	var obj=document.getElementById(objid);
	
	if(obj.style.display != "block"){
		this.className = 'active';
	}else{
		this.removeClass('active');
	}
}