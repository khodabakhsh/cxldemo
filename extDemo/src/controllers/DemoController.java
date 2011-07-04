/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Topics;
import models.TreeNode;
import models.TreeNodeVo;

import com.et.ar.exception.ActiveRecordException;
import com.et.mvc.Controller;
import com.et.mvc.JsonView;
import com.et.mvc.MultipartFile;
import com.et.mvc.MultipartRequest;
import com.mysql.jdbc.StringUtils;

public class DemoController extends Controller {
	/**
	 * 上传单个文件，保存文件
	 */
	public JsonView fileupload() throws IOException {

		MultipartRequest multiReq = (MultipartRequest) request;// 解析为multipart类型的
		MultipartFile photoPath = multiReq.getFile("photoPath");
		String photoName = multiReq.getParameter("photoName");
		InputStream in = photoPath.getInputStream();

		// 写入磁盘文件
		String writeToPath = multiReq.getRealPath("/") + "/temp/" + photoName
				+ ".jpg";
		OutputStream out = new FileOutputStream(writeToPath);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		out.close();
		in.close();

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("file", photoName);
		response.getWriter().write(new JsonView(result).toString());
		return null;
	}

	/**
	 * 上传多个文件，保存文件
	 */
	public JsonView multiuploadfile() throws IOException {

		MultipartRequest multiReq = (MultipartRequest) request;// 解析为multipart类型的
		MultipartFile[] file = multiReq.getFiles("file");

		for (MultipartFile attachment : file) {

			InputStream in = attachment.getInputStream();

			// 写入磁盘文件,这里会有拒绝访问，但是可以写进磁盘
			String writeToPath = multiReq.getRealPath("/") + File.separator
					+ "temp" + File.separator
					+ attachment.getOriginalFilename();
			OutputStream out = new FileOutputStream(writeToPath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			in.close();
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		response.getWriter().write(new JsonView(result).toString());
		return null;
	}

	public JsonView customSearchField() throws ActiveRecordException {
		String query = request.getParameter("query");
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));

		List<Topics> topics = Topics.findAll(Topics.class,
				"title like ? or author like ?", new String[] {
						"%" + query + "%", "%" + query + "%" }, "id asc",
				limit, start);

		long count = Topics.count(Topics.class,
				"title like ? or author like ?", new String[] {
						"%" + query + "%", "%" + query + "%" });
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("totalCount", count);
		result.put("topics", topics);
		return new JsonView(result);

	}

	public JsonView getAllTopics() throws ActiveRecordException {
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));

		List<Topics> topics = null;
		long count = 0;

		// 有搜索条件
		String fromFields = request.getParameter("fields");
		if (!StringUtils.isNullOrEmpty(fromFields)) {
			String query = request.getParameter("query");
			String[] fields = fromFields.substring(1, fromFields.length() - 1)
					.split(",");
			StringBuilder sb = new StringBuilder(" ");
			List obj = new ArrayList();
			for (String field : fields) {
				if (!StringUtils.isNullOrEmpty(field)) {
					sb.append("or " + field.substring(1, field.length() - 1)
							+ " like ? ");
					obj.add("%" + query + "%");
				}
			}

			topics = Topics.findAll(Topics.class, sb.toString().substring(3),
					obj.toArray(), "id asc", limit, start);
			count = Topics.count(Topics.class, sb.toString().substring(3), obj
					.toArray());
		} else {// 没有搜索条件

			topics = Topics.findAll(Topics.class, " 1=? ",
					new Integer[] { new Integer(1) }, "id asc", limit, start);
			count = Topics.count(Topics.class, " 1=?",
					new Integer[] { new Integer(1) });
		}
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("totalCount", count);
		result.put("topics", topics);
		return new JsonView(result);

	}

	public JsonView asyncTree() throws ActiveRecordException {
		String nodeId = request.getParameter("node");
		TreeNode parentNode = TreeNode.find(TreeNode.class, nodeId);
		List<TreeNode> childTreeNodes = parentNode.getChildTreeNodes();
		TreeNodeVo tnv = null;
		List<TreeNodeVo> tnvList = new ArrayList<TreeNodeVo>();
		;
		for (TreeNode tn : childTreeNodes) {
			tnv = new TreeNodeVo();
			tnv.setId(tn.getId());
			tnv.setText(tn.getText());
			tnv.setUrl(tn.getUrl());
			if (tn.getChildTreeNodes() != null
					&& tn.getChildTreeNodes().size() > 0) {
				tnv.setLeaf(false);
			} else {
				tnv.setLeaf(true);
			}
			tnvList.add(tnv);
			tnv = null;
		}

		return new JsonView(tnvList);

	}

	public JsonView getNode() throws ActiveRecordException {
		String mode = request.getParameter("mode");
		List<TreeNode> list = null;
		if (mode.equals("parent")) {
			list = TreeNode.findAll(TreeNode.class);

		}
		if (mode.equals("children")) {
			String parent = request.getParameter("parent");
			list = TreeNode.findAll(TreeNode.class, " parentId= ? ",
					new String[] { parent });
		}
		return new JsonView(list);
	}
}
