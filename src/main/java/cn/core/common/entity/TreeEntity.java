package cn.core.common.entity;

import cn.core.common.entity.tree.TreeNode;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TreeEntity class
 *
 * @author Administrator
 * @date
 */
public abstract class TreeEntity<T> extends AbstractEntity<String>
        implements TreeNode<String> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id; // 编号

    @TableField(value = "name")
    private String name; // 资源名称

    @TableField(value = "parent_id", strategy = FieldStrategy.IGNORED)
    private String parentId; // 父编号

    @TableField(value = "parent_ids", strategy = FieldStrategy.IGNORED)
    private String parentIds; // 父编号列表

    @TableField(exist = false)
    private Boolean expanded = Boolean.FALSE;
    @TableField(exist = false)
    private Boolean loaded = Boolean.TRUE;
    @TableField(exist = false)
    private T parent;


    /**
     * 是否有叶子节点
     */
    @TableField(exist = false)
    private boolean hasChildren;

    public boolean getHasChildren() {
        return this.hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String s) {
        this.id = s;
    }

    @Override
    public boolean isRoot() {
        if (getParentId() == null || getParentId().equals("") || getParentId().equals("0")) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getLevel() {
        if (parentIds == null) {
            return (long) 0;
        }
        String[] parentIdArr = parentIds.split("/");
        List<String> idsList = new ArrayList<String>();
        for (String id : parentIdArr) {
            if (!StringUtils.isEmpty(id)) {
                idsList.add(id);
            }
        }
        return (long) (idsList.size());
    }

    @Override
    public Boolean isLeaf() {
        if (getHasChildren()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public String getSeparator() {
        return "/";
    }

    @Override
    public String makeSelfAsNewParentIds() {
        if (StringUtils.isEmpty(getParentIds())) {
            return getId() + getSeparator();
        }
        return getParentIds() + getId() + getSeparator();
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getParentIds() {
        return parentIds;
    }

    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Override
    public Boolean getExpanded() {
        return expanded;
    }

    @Override
    public void setExpanded() {
        this.expanded = expanded;
    }

    @Override
    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public Boolean getLoaded() {
        return loaded;
    }

    @Override
    public String[] makeTags() {
        return new String[0];
    }


}
