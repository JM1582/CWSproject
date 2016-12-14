package com.sql;

import java.sql.*;
import java.util.*;

import com.model.*;

public class ActionPlanSQL extends DataBase {

	public boolean isExist(ActionPlan actionPlan){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from actionPlan where actionPlanId="+actionPlan.getId()+"";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return true;
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return false;
	}
	
	public ActionPlan setActionPlan(ActionPlan actionPlan){//save user data
		String strSQL = null;
		if(this.isExist(actionPlan)){
			this.updateActionPlan(actionPlan);
		} else {
			actionPlan = this.insertActionPlan(actionPlan);
		}
		this.setActionEntryMap(actionPlan);
		return actionPlan;
	}

	public void setActionEntryMap(ActionPlan actionPlan) {
		this.clearActionPlanAction(actionPlan);
		Map<Integer, ActionEntry> actionEntryMap = actionPlan.getActionEntryMap();
		Iterator<Integer> actionEntryIt = actionEntryMap.keySet().iterator();
		while(actionEntryIt.hasNext()){
			int actionEntryId = actionEntryIt.next();
			ActionEntry actionEntry = (ActionEntry) actionEntryMap.get(actionEntryId);
			this.setActionEntry(actionEntry);
			Map<Integer, Action> actionMap = actionEntry.getActionMap();
			Iterator<Integer> actionIt = actionMap.keySet().iterator();
			while(actionIt.hasNext()){
				int actionId = actionIt.next();
				Action action = actionMap.get(actionId);
				this.setAction(action);
				String strSQL = "insert into actionPlan_action values("
						+ Integer.valueOf(actionPlan.getId())+","
						+ Integer.valueOf(actionEntryId)+","
						+ Integer.valueOf(actionId)+")";
				try{
					st.executeUpdate(strSQL);
				} catch (Exception e){
					System.out.println("Fail: "+strSQL);
					e.printStackTrace();
				}
			}
		}
		
	}

	private Action setAction(Action action) {
		if(this.isExistAction(action)){
			this.updateAction(action);
		} else {
			action = this.insertAction(action);
		}
		return action;
	}

	public void updateAction(Action action){
		String strSQL = "update actionEntry set "
					+ "actionId="+action.getId()+", "
					+ "intervention='"+action.getIntervention()+"', "
					+ "careProviderId="+action.getCareProvider().getId()+", "
					+ "comment='"+action.getComment()+"' "
					+ "where action='"+action.getId()+"'";
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public Action insertAction(Action action){
		String strSQL = "insert into actionEntry("
				+ "actionId,"
				+ "intervention,"
				+ "careProviderId,"
				+ "comment) "
				+ "values(?,?,?,?)";
		PreparedStatement st = null;
		try {
			st = this.conn.prepareStatement(strSQL,Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			st.setInt(1, action.getId());
			st.setString(2, action.getIntervention());
			st.setInt(3, action.getCareProvider().getId());
			st.setString(4, action.getComment());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try{
			st.executeUpdate();
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		try {
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()){
				action.setId(rs.getInt(1));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return action;
	}

	public boolean isExistAction(Action action) {
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from action where actionId="+action.getId()+"";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return true;
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return false;
	}

	public ActionEntry setActionEntry(ActionEntry actionEntry) {
		if(this.isExistActionEntry(actionEntry)){
			this.updateActionEntry(actionEntry);
		} else {
			actionEntry = this.insertActionEntry(actionEntry);
		}
		return actionEntry;
	}
	
	public void updateActionEntry(ActionEntry actionEntry){
		String strSQL = "update actionEntry set "
					+ "actionEntryId="+actionEntry.getId()+", "
					+ "domainId='"+actionEntry.getDomain().getId()+"', "
					+ "cScore="+actionEntry.getCscore()+", "
					+ "fScore="+actionEntry.getFscore()+" "
					+ "where actionEntryId='"+actionEntry.getId()+"'";
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public ActionEntry insertActionEntry(ActionEntry actionEntry){
		String strSQL = "insert into actionEntry("
				+ "actionEntryId,"
				+ "domainId,"
				+ "cScore,"
				+ "fScore) "
				+ "values(?,?,?,?)";
		PreparedStatement st = null;
		try {
			st = this.conn.prepareStatement(strSQL,Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			st.setInt(1, actionEntry.getId());
			st.setString(2, actionEntry.getDomain().getId());
			st.setString(3, actionEntry.getCscore());
			st.setString(4, actionEntry.getFscore());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try{
			st.executeUpdate();
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		try {
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()){
				actionEntry.setId(rs.getInt(1));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actionEntry;
	}

	public boolean isExistActionEntry(ActionEntry actionEntry) {
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from actionEntry where actionEntryId="+actionEntry.getId()+"";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return true;
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return false;
	}

	public ActionPlan insertActionPlan(ActionPlan actionPlan) {
		String strSQL = "insert into actionPlan("
				+ "CWSNumber,"
				+ "authorId,"
				+ "date,"
				+ "sign) "
				+ "values(?,?,?,?)";
		PreparedStatement st = null;
		try {
			st = this.conn.prepareStatement(strSQL,Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			st.setString(1, actionPlan.getCWSNumber());
			st.setInt(2, actionPlan.getAuthor().getId());
			st.setString(3, actionPlan.getDate());
			st.setBoolean(4, actionPlan.getSign());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try{
			st.executeUpdate();
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		try {
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()){
				actionPlan.setId(rs.getInt(1));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actionPlan;
	}

	public void updateActionPlan(ActionPlan actionPlan) {
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "update actionPlan set "
					+ "actionPlanId="+Integer.toString(actionPlan.getId())+","
					+ "CWSNumber='"+actionPlan.getCWSNumber()+"',"
					+ "authorId='"+actionPlan.getAuthor().getId()+"',"
					+ "date='"+actionPlan.getDate()+"',"
					+ "sign="+actionPlan.getSign()+" "
					+ "where actionPlanId="+Integer.toString(actionPlan.getId());
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public ActionPlan getAcionPlan(int actionPlanId){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from actionPlan where actionPlanId="+Integer.toString(actionPlanId);
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				UserSQL userSQL = new UserSQL();
				CareProvider careProvider = userSQL.getUser(rs.getInt("authorId")).toCareProvider();
				userSQL.disconnect();
					
				ActionPlan actionPlan = new ActionPlan(rs.getString("CWSNumber"), careProvider);
				actionPlan.setDate(rs.getString("date"));
				actionPlan.setSign(rs.getBoolean("sign"));
				
				actionPlan = this.getActionEntryMap(actionPlan);
				
				return  actionPlan;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}

	public ActionPlan getActionEntryMap(ActionPlan actionPlan) {
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from actionPlan_action where actionPlanId="+Integer.toString(actionPlan.getId());
		try{
			ResultSet rs = st.executeQuery(strSQL);
			while(rs.next()){
				int actionEntryId = rs.getInt("actionEntryId");
				if(!actionPlan.getActionEntryMap().containsKey(actionEntryId)){
					ActionEntry actionEntry = this.getActionEntry(actionEntryId, actionPlan.getId());
					actionPlan.addActionEntry(actionEntry);
				}
			}
			return actionPlan;
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}

	private ActionEntry getActionEntry(int actionEntryId, int actionPlanId) {
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from actionEntry where actionEntryId="+Integer.toString(actionEntryId);
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				ActionEntry actionEntry = new ActionEntry(actionEntryId);
				FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
				formTemplateSQL.connet();
				Domain domain = formTemplateSQL.getDomain(rs.getString("domainId"));
				formTemplateSQL.disconnect();
				actionEntry.setDomain(domain);
				actionEntry.setCscore(rs.getString("cScore"));
				actionEntry.setFscore(rs.getString("fScore"));
				
				actionEntry = this.getActionMap(actionEntry, actionPlanId);
				
				return  actionEntry;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}

	public ActionEntry getActionMap(ActionEntry actionEntry, int actionPlanId) {
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from actionPlan_action where "
				+ "actionEntryId="+Integer.toString(actionEntry.getId())+" and "
				+ "actionPlanId="+Integer.toString(actionPlanId);
		try{
			ResultSet rs = st.executeQuery(strSQL);
			while(rs.next()){
				int actionId = rs.getInt("actionId");
				if(!actionEntry.getActionMap().containsKey(actionId)){
					Action action = this.getAction(actionId);
					actionEntry.addAction(action);
				}
			}
			return actionEntry;
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}

	private Action getAction(int actionId) {
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from action where actionId="+Integer.toString(actionId);
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				Action action = new Action(actionId);
				action.setIntervention(rs.getString("intervention"));
				
				UserSQL userSQL = new UserSQL();
				userSQL.connet();
				CareProvider careProvider = userSQL.getUser(rs.getInt("careProviderId")).toCareProvider();
				userSQL.disconnect();
				
				action.setCareProvider(careProvider);
				action.setComment(rs.getString("comment"));
				
				return  action;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}

	public void clearActionPlanAction(ActionPlan actionPlan){
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String strSQL = "delete from actionPlan_action where actionPlanId="+actionPlan.getId();
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
}
