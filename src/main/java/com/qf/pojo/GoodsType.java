package com.qf.pojo;

/*
`id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `level` int(11) DEFAULT NULL,
  `Parent` int(11) DEFAULT NULL,
 */
public class GoodsType {
    private Integer id;
    private String name;
    private Integer level;
    private Integer Parent;

    public GoodsType(Integer id, String name, Integer level, Integer parent) {
        this.id = id;
        this.name = name;
        this.level = level;
        Parent = parent;
    }

    public GoodsType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParent() {
        return Parent;
    }

    public void setParent(Integer parent) {
        Parent = parent;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", Parent=" + Parent +
                '}';
    }
}
