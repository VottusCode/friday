package cz.maku.friday.storage.sql;

import cz.maku.friday.player.FridayPlayer;

public class SQLValue {

  private final FridayPlayer fp;
  private final String value;
  private final SQLStorage sqlStorage;

  public SQLValue(FridayPlayer fp, String table) {
    this.fp = fp;
    this.value = table;
    sqlStorage = new SQLStorage(value);
  }

  public String loadSqlValue() {
    return sqlStorage.selectString("nick", fp.getName(), value);
  }

  public int loadSqlIntValue() {
    if (sqlStorage.selectInteger("nick", fp.getName(), value) == -1) {
      Insert ins = new Insert();
      ins.setColumns(value + ", nick");
      ins.setData(String.valueOf(0), fp.getName());
      sqlStorage.insert(ins);
      return 0;
    } else {
      return sqlStorage.selectInteger("nick", fp.getName(), value);
    }
  }

  public void updateSqlValue(String key) {
    sqlStorage.update("nick", fp.getName(), value, key);
  }

  public void updateSqlValue(int key) {
    sqlStorage.update("nick", fp.getName(), value, key);
  }

  public void addSqlValue(int key) {
    sqlStorage.update("nick", fp.getName(), value, loadSqlIntValue() + key);
  }

  public void removeSqlValue(int key) {
    sqlStorage.update("nick", fp.getName(), value, loadSqlIntValue() - key);
  }
}
