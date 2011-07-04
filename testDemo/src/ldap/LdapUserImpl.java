package ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

public class LdapUserImpl {

	private String strInitCtx;
	private String host;
	private String searchBase;
	private String filter;
	private DirContext ctx;
	private Hashtable env;
	private SearchControls constraints;

	/**
	 * 
	 */
	public LdapUserImpl() throws Exception {
		env = new Hashtable();
		Config config = Config.newInstance();
		constraints = new SearchControls();
		constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchBase = config.getServerDomain();
		filter = "";
		env.put(Context.INITIAL_CONTEXT_FACTORY, config.getLDAPInitContext());
		// env.put(Context.PROVIDER_URL, "ldap://" + config.getServerIP() +
		// ":"+config.getServerPort());
		env.put(Context.PROVIDER_URL, "ldap://128.8.1.213:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");

	}
/**
 * ldap验证
 * @param principal ，用户名
 * @param password ，密码
 * @return
 */
	public boolean verifyUserPassword(String principal, String password) {
		boolean result = false;
		// Setup LDAP env variables
		if (password == null || "".equals(password))
			return false;
		try {
			// get a handle to an Initial Dircontext
			env.put(Context.SECURITY_PRINCIPAL, principal);
			env.put(Context.SECURITY_CREDENTIALS, password);
			DirContext ctx = new InitialDirContext(env);
			if (ctx != null)
				result = true;

		} catch (Exception e) {
			System.out
					.println("LDAP:error, invalid credentials,ldap login refuse");
			result = false;
		}
		return result;
	}
}
