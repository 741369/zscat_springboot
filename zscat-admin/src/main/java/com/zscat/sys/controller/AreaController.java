package com.zscat.sys.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.assertj.core.util.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zscat.sys.model.SysArea;
import com.zscat.sys.service.SysAreaService;

@Controller
@RequestMapping("area")
public class AreaController {

	@Resource
	private SysAreaService sysAreaService;

	@RequestMapping
	public String toArea(Model model) {
	//	model.addAttribute("treeList", JSON.toJSONString(sysAreaService..select(new SysArea()));
		return "sys/area/area";
	}

	/**
	 * 区域树
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.POST)
	public @ResponseBody List<SysArea> getAreaTreeList() {
		List<SysArea> list = sysAreaService.select(new SysArea());
		return list;
	}

	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysArea sysArea) {
		return sysAreaService.insertOrUpdate(sysArea);
	}

	/**
	 * 删除区域及其子区域
	 * 
	 * @param resourceId
	 *            区域id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Integer dels(Long id) {
		Integer count = 0;
		if (null != id) {
			count = sysAreaService.deleteByPrimaryKey(id);
		}
		return count;
	}

	/**
	 * 分页显示区域table
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model) {
		PageHelper.startPage(params);
		List<SysArea> list = sysAreaService.select(new SysArea());
		model.addAttribute("page", new PageInfo<SysArea>(list));
		return "sys/area/area-list";
	}

	/**
	 * 弹窗
	 * 
	 * @param id
	 * @param parentId
	 *            父类id
	 * @param mode
	 *            模式(add,edit,detail)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showLayer(Long id, Long parentId,
			@PathVariable("mode") String mode, Model model) {
		SysArea area = null, pArea = null;
		
		model.addAttribute("pArea", pArea).addAttribute("area", area);
		return mode.equals("detail") ? "sys/area/area-detail"
				: "sys/area/area-save";
	}

	
}
