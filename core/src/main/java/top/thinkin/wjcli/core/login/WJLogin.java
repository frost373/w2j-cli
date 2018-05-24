package top.thinkin.wjcli.core.login;

public interface WJLogin<T> {
      String login(String login, String pass, T context);

      boolean filter(String auth, T context);

}
