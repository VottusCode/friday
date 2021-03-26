package cz.maku.friday.storage.sql;

import java.util.List;

public class Insert {

  private String columns = "";
  private String[] data = null;

  public Insert(String columns, String... data) {
    this.columns = columns;
    this.data = data;
  }

  public Insert() {}

  public void setColumns(String columns) {
    this.columns = columns;
  }

  public void setData(String... data) {
    this.data = data;
  }

  public void setData(List<String> data) {
    this.data = (String[]) ((String[]) data.toArray());
  }

  public String getColumns() {
    return this.columns;
  }

  public String[] getData() {
    return this.data;
  }
}
