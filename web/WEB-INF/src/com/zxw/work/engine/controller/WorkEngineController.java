/**
 * 工作流引擎控制器
 */

package com.zxw.work.engine.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zxw.organization.model.User;
import com.zxw.system.extjs.SelectedOption;
import com.zxw.system.extjs.TreePanel;
import com.zxw.system.web.BaseController;
import com.zxw.system.web.JsonResult;
import com.zxw.system.web.PageInfo;
import com.zxw.util.StringUtil;
import com.zxw.work.engine.model.WorkItem;
import com.zxw.work.engine.service.WorkEngineService;


@Controller
@RequestMapping("/work/engine")
public class WorkEngineController extends BaseController{
	
	@Autowired
	private WorkEngineService workEngineService = null;
	
	/**
	 * 获得工作模型的下拉选择框的数据
	 * @return
	 */
	@RequestMapping("/getAllWorkModel_selected")
	@ResponseBody
	public List<SelectedOption> getAllWorkModel_selected() {
		return workEngineService.getAllWorkModel_selected();
	}
	
	/**
	 * 获得工作项的下拉选择框的数据
	 * @return
	 */
	@RequestMapping("/getAllWorkItem_selected")
	@ResponseBody
	public List<SelectedOption> getAllWorkItem_selected() {
		return workEngineService.getAllWorkItem_selected();
	}
	
	/**
	 * 获得所有的工作模型 列表
	 * @return
	 */
	@RequestMapping("/getModelTree")
	@ResponseBody
	public List<TreePanel> getModelTree() {
		return workEngineService.getModelTree();
	}
	
	/**
	 * 获得所有的工作模型 列表
	 * @return
	 */
	@RequestMapping(value="/addOrUpdateWorkItem", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult addOrUpdateWorkItem(HttpServletRequest request, @RequestBody WorkItem workItem) {
		return workEngineService.addOrUpdateWorkItem(workItem);
	}
	
	/**
	 * 增加部门
	 * @param dept
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult addDept(HttpServletRequest request, @RequestBody WorkItem workItem) {
		
		return new JsonResult();
	}
	
	
	/**
	 * 获得当前用户待办的工作的列表
	 * @return
	 */
	@RequestMapping(value="/todo/pageInfo", method=RequestMethod.POST)
	@ResponseBody
	public PageInfo todoWorkPageInfoByCurrentUser(HttpServletRequest request, 
			@RequestParam(required=true) int start, 
			@RequestParam(required=true) int limit) {
		User user = super.getCurrentUser(request);
		if(user != null) {
			return workEngineService.todoWorkPageInfoByUser(user.getId(), start, limit);
		}
		return new PageInfo();
	}
	
	
	/**
	 * 工作项 对应的表单显示
	 * @param request
	 * @param itemID
	 * @return
	 */
	@RequestMapping(value="/item/{item}", method=RequestMethod.GET)
	public String itemFormShow(HttpServletRequest request, 
			@PathVariable String item) {
		
		if(StringUtil.isEntity(item)) {
			return workEngineService.getItemForm(item);
		}
		return "404";
	}

}
