package com.sql;

import java.sql.*;
import java.util.*;

import com.model.*;

public class ActionPlanSQL extends DataBase {

	public boolean isExist(ActionPlan actionPlan) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from actionPlan where actionPlanId="+actionPlan.getId()+"";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return true;
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return false;
	}
	
	public ActionPlan setActionPlan(ActionPlan actionPlan) throws Exception{//save user data
		String strSQL = null;
		if(this.isExist(actionPlan)){
			this.updateActionPlan(actionPlan);
		} else {
			actionPlan = this.insertActionPlan(actionPlan);
		}
		this.setActionEntryMap(actionPlan);
		return actionPlan;
	}

	public void setActionEntryMap(ActionPlan actionPlan) throws Exception {
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
					throw e;
				}
			}
		}
		
	}

	public Action setAction(Action action) throws Exception {
		if(this.isExistAction(action)){
			this.updateAction(action);
		} else {
			action = this.insertAction(action);
		}
		return action;
	}

	public void updateAction(Action action) throws Exception{
		String strSQL = "update action set "
					+ "actionId="+action.getId()+", "
					+ "intervention='"+action.getIntervention()+"', "
					+ "careProviderId="+action.getCareProvider().getId()+", "
					+ "comment='"+action.getComment()+"' "
					+ "where actionId='"+action.getId()+"'";
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}
	
	public Action insertAction(Action action) throws Exception{
		String strSQL = "insert into action("
				+ "actionId,"
				+ "intervention,"
				+ "careProviderId,"
				+ "comment) "
				+ "values(?,?,?,?)";
		PreparedStatement st = null;
		st = this.conn.prepareStatement(strSQL,Statement.RETURN_GENERATED_KEYS);
		st.setInt(1, action.getId());
		st.setString(2, action.getIntervention());
		st.setInt(3, action.getCareProvider().getId());
		st.setString(4, action.getComment());
			
		try{
			st.executeUpdate();
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		
		ResultSet rs = st.getGeneratedKeys();
		if(rs.next()){
			action.setId(rs.getInt(1));
		}
		rs.close();
		return action;
	}

	public boolean isExistAction(Action action) throws Exception {
		st = conn.createStatement();
		String strSQL = "select * from action where actionId="+action.getId()+"";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return true;
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return false;
	}

	public ActionEntry setActionEntry(ActionEntry actionEntry) throws Exception {
		if(this.isExistActionEntry(actionEntry)){
			this.updateActionEntry(actionEntry);
		} else {
			actionEntry = this.insertActionEntry(actionEntry);
		}
		return actionEntry;
	}
	
	public void updateActionEntry(ActionEntry actionEntry) throws Exception{
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
			throw e;
		}
	}
	
	public ActionEntry insertActionEntry(ActionEntry actionEntry) throws Exception{
		String strSQL = "insert into actionEntry("
				+ "actionEntryId,"
				+ "domainId,"
				+ "cScore,"
				+ "fScore) "
				+ "values(?,?,?,?)";
		PreparedStatement st = null;
		st = this.conn.prepareStatement(strSQL,Statement.RETURN_GENERATED_KEYS);
		st.setInt(1, actionEntry.getId());
		st.setString(2, actionEntry.getDomain().getId());
		st.setString(3, actionEntry.getCscore());
		st.setString(4, actionEntry.getFscore());
		
		try{
			st.executeUpdate();
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		ResultSet rs = st.getGeneratedKeys();
		if(rs.next()){
			actionEntry.setId(rs.getInt(1));
		}
		rs.close();
		return actionEntry;
	}

	public boolean isExistActionEntry(ActionEntry actionEntry) throws Exception {
		st = conn.createStatement();
		String strSQL = "select * from actionEntry where actionEntryId="+actionEntry.getId()+"";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return true;
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return false;
	}

	public ActionPlan insertActionPlan(ActionPlan actionPlan) throws Exception {
		String strSQL = "insert into actionPlan("
				+ "CWSNumber,"
				+ "authorId,"
				+ "date,"
				+ "sign) "
				+ "values(?,?,?,?)";
		PreparedStatement st = null;
		st = this.conn.prepareStatement(strSQL,Statement.RETURN_GENERATED_KEYS);
		
		st.setString(1, actionPlan.getCWSNumber());
		st.setInt(2, actionPlan.getAuthor().getId());
		st.setString(3, actionPlan.getDate());
		st.setBoolean(4, actionPlan.getSign());
		
		try{
			st.executeUpdate();
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		ResultSet rs = st.getGeneratedKeys();
		if(rs.next()){
			actionPlan.setId(rs.getInt(1));
		}
		rs.close();
		return actionPlan;
	}

	public void updateActionPlan(ActionPlan actionPlan) throws Exception {
		st = conn.createStatement();
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
			throw e;
		}
	}
	
	public Map getActionPlanByCWSNumber(String CWSNumber) throws Exception{
		Map<Integer, ActionPlan> actionPlanMap = new HashMap<Integer, ActionPlan>();
		st = conn.createStatement();
		String strSQL = "select * from actionPlan where CWSNumber='"+CWSNumber+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			while(rs.next()){
				ActionPlan actionPlan = this.getAcionPlan(rs.getInt("actionPlanId"));
				actionPlanMap.put(actionPlan.getId(), actionPlan);
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return actionPlanMap;
	}
	
	public ActionPlan getAcionPlan(int actionPlanId) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from actionPlan where actionPlanId="+Integer.toString(actionPlanId);
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				UserSQL userSQL = new UserSQL();
				userSQL.connect();
				CareProvider careProvider = userSQL.getUser(rs.getInt("authorId")).toCareProvider();
				userSQL.disconnect();
					
				ActionPlan actionPlan = new ActionPlan(rs.getString("CWSNumber"), careProvider);
				actionPlan.setId(actionPlanId);
				actionPlan.setDate(rs.getString("date"));
				actionPlan.setSign(rs.getBoolean("sign"));
				
				actionPlan = this.getActionEntryMap(actionPlan);
				
				return  actionPlan;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public ActionPlan getActionEntryMap(ActionPlan actionPlan) throws Exception {
		
		st = conn.createStatement();
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
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return actionPlan;
	}

	public ActionEntry getActionEntry(int actionEntryId, int actionPlanId) throws Exception {
		st = conn.createStatement();
		String strSQL = "select * from actionEntry where actionEntryId="+Integer.toString(actionEntryId);
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				ActionEntry actionEntry = new ActionEntry(actionEntryId);
				FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
				formTemplateSQL.connect();
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
			throw e;
		}
		return null;
	}

	public ActionEntry getActionMap(ActionEntry actionEntry, int actionPlanId) throws Exception {
		st = conn.createStatement();
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
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return actionEntry;
	}

	public Action getAction(int actionId) throws Exception {
		st = conn.createStatement();
		String strSQL = "select * from action where actionId="+Integer.toString(actionId);
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				Action action = new Action(actionId);
				action.setIntervention(rs.getString("intervention"));
				
				UserSQL userSQL = new UserSQL();
				userSQL.connect();
				CareProvider careProvider = userSQL.getUser(rs.getInt("careProviderId")).toCareProvider();
				userSQL.disconnect();
				
				action.setCareProvider(careProvider);
				action.setComment(rs.getString("comment"));
				
				return  action;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public void clearActionPlanAction(ActionPlan actionPlan) throws SQLException{
		st = conn.createStatement();
		String strSQL = "delete from actionPlan_action where actionPlanId="+actionPlan.getId();
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}
}
