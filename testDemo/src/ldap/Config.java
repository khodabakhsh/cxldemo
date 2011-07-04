package ldap;

public class Config {
	private static Config configInstance;
	private String isUseLdap;
	private String serverDomain;
	private String serverIP;
	private String serverPort;
	private String serverUser;
	private String serverPWD;
	private String LDAPInitContext;
	private String LDAPPersonClassName;
	private String defaultRoleId;
	private String defaultSysAdminUserId;
	private String defaultSysAdminUserName;

	public String getLDAPInitContext() {
		return LDAPInitContext;
	}

	public Config() throws Exception {
		try {
			XMLParaReader reader = new XMLParaReader("/nsacl-config.xml");
			reader.setRoot("ldap-info");
			LDAPInitContext = reader.getTextValue("context-factory");
			isUseLdap = reader.getTextValue("isUseLdap");
			serverPort = reader.getTextValue("server-port");
			serverUser = reader.getTextValue("connect-username");
			serverPWD = reader.getTextValue("connect-password");
			serverIP = reader.getTextValue("server-ip");
			serverDomain = reader.getTextValue("domain-fullname");
//			reader.setRoot("miscellaneous");
//			defaultRoleId = reader.getTextValue("defaultRoleId");
//			defaultSysAdminUserId = reader
//					.getTextValue("defaultSysAdminUserId");
//			defaultSysAdminUserName = reader
//					.getTextValue("defaultSysAdminUserName");
//			LDAPPersonClassName = "dominoPerson";
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public static boolean isUseLdap() {
		XMLParaReader reader = null;
		try {
			reader = new XMLParaReader("/nsacl-config.xml");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		reader.setRoot("ldap-info");
		return reader.getTextValue("isUseLdap").equals("1") ? true : false;
	}

	public static Config newInstance() throws Exception {
		if (configInstance == null)
			configInstance = new Config();
		return configInstance;
	}

	public String getServerIP() {
		return serverIP;
	}

	public String getServerPort() {
		return serverPort;
	}

	public String getServerPWD() {
		return serverPWD;
	}

	public String getServerDomain() {
		return serverDomain;
	}

	public String getServerUser() {
		return serverUser;
	}

	public String getLDAPPersonClassName() {
		return LDAPPersonClassName;
	}

	public String getIsUseLdap() {
		return isUseLdap;
	}

	public String getDefaultRoleId() {
		return defaultRoleId;
	}

	public String getDefaultSysAdminUserId() {
		return defaultSysAdminUserId;
	}

	public String getDefaultSysAdminUserName() {
		return defaultSysAdminUserName;
	}

}
