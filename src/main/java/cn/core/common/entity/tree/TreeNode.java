package cn.core.common.entity.tree;

public interface TreeNode<ID> {
    ID getId();

    void setId(ID name);

    boolean isRoot();

    /**
     * @return 节点名称
     */
    public String getName();

    /**
     * @param name 节点名称
     */
    void setName(String name);

    /**
     * @return 节点的级别，默认最高级为0
     */
    Long getLevel();

    /**
     * @return 是否为叶节点
     */
    Boolean isLeaf();

    /**
     * @return获取 parentIds 之间的分隔符
     */
    String getSeparator();

    /**
     * @return 把自己构造出新的父节点路径
     */
    String makeSelfAsNewParentIds();

    /**
     * @return 父路径
     */
    ID getParentId();

    /**
     * @param parentId 设置父路径
     */
    void setParentId(ID parentId);

    /**
     * @return 所有父路径 如1, 2, 3,
     */
    String getParentIds();

    /**
     * @param parentIds 设置所有父路径
     */
    void setParentIds(String parentIds);

    /**
     * @return 是否默认展开状态
     */
    Boolean getExpanded();

    /**
     * 设置默认展开状态
     */
    void setExpanded();

    /**
     * @param loaded 是否已经加载过子节点（为false时点击节点会自动加载子节点）
     */
    void setLoaded(Boolean loaded);

    /**
     * @return 设置loaded
     */
    public Boolean getLoaded();

    public String[] makeTags();

}
