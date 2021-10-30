package wnxy.wn44.ssp.entity;

/**
 * @author Administrator
 */
public class Permission {

    private Integer id;
    private String permissionsName;
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id='" + id + '\'' +
                ", permissionsName='" + permissionsName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
