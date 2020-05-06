package com.amdocs.media.assignment.database;

import java.util.List;
import java.util.Map;

public interface UserDao {

	public int update(String tblName, Map<String, Object> columnValueMap, Map<String, Object> whereColumnValueMap);

	public List<Map<String, Object>> getData(String queryCode, String primaryCode);

	public int delete(String tblName, String columnName, Object columnValue);
	
	public int insert(String tblName, Map<String, Object> columnValueMap);

}
