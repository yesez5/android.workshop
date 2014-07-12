package com.cocomsys.db101.db;

/**
 * Created by yesez on 07-12-14.
 */
public class CountryDbHandler {
	public static final String NAME = "countries";

	public interface Fields {
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String ABREV = "abrev";
	}

	public interface Sentences {
		public static final String CREATE =
				String.format("CREATE TABLE %s (%s INTEGER, %s TEXT, %s TEXT)",
							  NAME, Fields.ID, Fields.NAME, Fields.ABREV);
		public static final String DELETE =
				String.format("DROP TABLE IF EXISTS %s", NAME);
		public static final String SELECT_ALL =
				String.format("SELECT * FROM %s", NAME);
	}
}
