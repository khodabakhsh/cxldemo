/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.List;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.BelongsTo;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.DependentType;
import com.et.ar.annotations.HasMany;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

/**
 *
 * @author cxl
 */
@Table(name="treenode")
public class TreeNode extends ActiveRecordBase{
    @Id private Integer id;
   
    @Column
    private String text;
    
    @Column
    private String url;
 
    @HasMany(foreignKey="parentId",dependent=DependentType.DELETE)
    private List<TreeNode> childTreeNodes;
    
    @BelongsTo(foreignKey="parentId")
    private TreeNode parentTreeNode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TreeNode> getChildTreeNodes() {
		return childTreeNodes;
	}

	public void setChildTreeNodes(List<TreeNode> childTreeNodes) {
		this.childTreeNodes = childTreeNodes;
	}

	public TreeNode getParentTreeNode() {
		return parentTreeNode;
	}

	public void setParentTreeNode(TreeNode parentTreeNode) {
		this.parentTreeNode = parentTreeNode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
