package com.amdocs.media.assignment.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Repository
@PropertySource(value = { "classpath:database.properties" })
public class UserDaoImpl implements UserDao {
	@Autowired
	DatabaseConfig databaseConfig;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Environment env;

	@Transactional
	public int update(String tblName, Map<String, Object> columnValueMap, Map<String, Object> whereColumnValueMap) {
		String query;
		String setClause;
		String whereClause;
		List<Object> valueList = new ArrayList<>();

		Function<Map<String, Object>, String> setClauseMaker = map -> map.entrySet().stream().map(entry -> {
			valueList.add(entry.getValue());
			return format("%s = ?", entry.getKey());
		}).collect(Collectors.joining(","));

		Function<Map<String, Object>, String> whereClauseMaker = map -> map.entrySet().stream().map(entry -> {
			valueList.add(entry.getValue());
			return format("%s = ?", entry.getKey());
		}).collect(Collectors.joining(" and "));

		setClause = setClauseMaker.apply(columnValueMap);
		whereClause = whereClauseMaker.apply(whereColumnValueMap);

		Object[] val = valueList.toArray(new Object[0]);
		query = String.format("UPDATE %s SET %s WHERE %s", tblName, setClause, whereClause);
		return jdbcTemplate.update(query, val);
	}

	public List<Map<String, Object>> getData(String queryCode, String primaryKey) {

		String qry = env.getProperty(queryCode);
		String qry1;
		if (qry != null && qry.contains("?")) {
			qry1 = qry.replace("?", primaryKey);
		} else {
			qry1 = String.format("%s '%s'", env.getProperty(queryCode), primaryKey);
		}
		return jdbcTemplate.queryForList(qry1);
	}

	@Transactional
	public int delete(String tblName, String columnName, Object id) {
		String sql = String.format("delete from %s where %s = '%s'", tblName, columnName, id);
		int res = jdbcTemplate.update(sql);
		return res;
	}

	@Transactional
	public int insert(String tblName, Map<String, Object> columnValueMap) {

		List<String> col = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		String query;
		String wildChar;
		String columnList;
		int result = 0;

		for (Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
			col.add(entry.getKey());
			values.add(entry.getValue());
		}

		wildChar = IntStream.range(0, col.size()).boxed().map(i -> "?").collect(Collectors.joining(","));
		columnList = String.join(",", col);

		query = format("INSERT INTO %s (%s) VALUES (%s)", tblName, columnList, wildChar);
		Object[] val = values.toArray(new Object[0]);
		result = jdbcTemplate.update(query, val);

		return result;
	}
}
