package testLoginKaiXin;

import java.io.FileReader;
import java.io.LineNumberReader;

import org.mozilla.javascript.*;

public final class KaiXinJS {
    private Context cx;
    private Scriptable scope;

    public KaiXinJS() {
        this.cx = Context.enter();
        this.scope = cx.initStandardObjects();
    }

    public Object runJavaScript(String filename) {
        String jsContent = this.getJsContent(filename);
        Object result = cx.evaluateString(scope, jsContent, filename, 1, null);
        return result;
    }

    private String getJsContent(String filename) {
        LineNumberReader reader;
        try {
            reader = new LineNumberReader(new FileReader(filename));
            String s = null;
            StringBuffer sb = new StringBuffer();
            while ((s = reader.readLine()) != null) {
                sb.append(s).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Scriptable getScope() {
        return scope;
    }

    public String getPassword(String oriPassword, String key) {
        String filename = System.getProperty("user.dir") + "/resources/testLoginKaiXin/kaixin001.js";

        @SuppressWarnings("unused")
        Object result = runJavaScript(filename);
        Scriptable scope = getScope();

        Function getPassword = (Function) scope.get("getPassword", scope);
        Object password = getPassword.call(Context.getCurrentContext(), scope,
                getPassword, new Object[] { oriPassword, key });
        return Context.toString(password);
    }
}