package cn.javaex.yaoqishan.action.portal;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.javaex.yaoqishan.exception.QingException;
import cn.javaex.yaoqishan.service.channel_info.ChannelInfoService;
import cn.javaex.yaoqishan.service.field_info.FieldInfoService;
import cn.javaex.yaoqishan.service.media_info.MediaInfoService;
import cn.javaex.yaoqishan.service.nav_info.NavInfoService;
import cn.javaex.yaoqishan.service.seo_info.SeoInfoService;
import cn.javaex.yaoqishan.service.template_info.TemplateInfoService;
import cn.javaex.yaoqishan.service.type_info.TypeInfoService;
import cn.javaex.yaoqishan.service.user_info.UserInfoService;
import cn.javaex.yaoqishan.service.video_info.VideoInfoService;
import cn.javaex.yaoqishan.service.web_info.WebInfoService;
import cn.javaex.yaoqishan.view.ChannelInfo;
import cn.javaex.yaoqishan.view.FieldInfo;
import cn.javaex.yaoqishan.view.NavInfo;
import cn.javaex.yaoqishan.view.SeoInfo;
import cn.javaex.yaoqishan.view.TypeInfo;
import cn.javaex.yaoqishan.view.UserInfo;
import cn.javaex.yaoqishan.view.VideoInfo;
import cn.javaex.yaoqishan.view.WebInfo;

@Controller
@RequestMapping("portal")
public class PortalAction {

	@Autowired
	private VideoInfoService videoInfoService;
	@Autowired
	private MediaInfoService mediaInfoService;
	@Autowired
	private FieldInfoService fieldInfoService;
	@Autowired
	private NavInfoService navInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private WebInfoService webInfoService;
	@Autowired
	private SeoInfoService seoInfoService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private TemplateInfoService templateInfoService;
	@Autowired
	private TypeInfoService typeInfoService;
	
	/**
	 * ????????????
	 * @return
	 */
	@RequestMapping("index.action")
	public String index(ModelMap map, HttpServletRequest request) {
		// ????????????
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// ????????????seo
		SeoInfo seoInfo = seoInfoService.selectByType("index");
		map.put("seoInfo", seoInfo);
		
		// ??????????????????
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");
		
		// ??????????????????
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		for (NavInfo navInfo : navlist) {
			// ?????????????????????
			if ("1".equals(navInfo.getIsIndex())) {
				// ????????????????????????????????????????????????????????????
				if ("system".equals(navInfo.getType())) {
					// ??????????????????
					if ("portal/index.action".equals(navInfo.getLink())) {
						map.put("active", navInfo.getLink());
						return "portal/pc/template/" + templatePC + "/index";
					} else {
						// ??????
						String channelId = navInfo.getChannelId();
						map.put("active", "portal/portal.action?channelId="+channelId);
						ChannelInfo channelInfo = channelInfoService.selectById(channelId);
						return "portal/pc/template/" + templatePC + "/channel/" + channelInfo.getTemplate();
					}
				} else {
					// ???????????????
					map.put("active", navInfo.getLink());
					return "redirect:"+navInfo.getLink();
				}
			}
		}
		
		return "portal/pc/template/" + templatePC + "/index";
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping("portal.action")
	public String portal(ModelMap map,
			HttpServletRequest request,
			@RequestParam(value="channelId") String channelId) {
		// ????????????
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");

		// ???????????????
		try {
			Integer.parseInt(channelId);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		ChannelInfo channelInfo = channelInfoService.selectById(channelId);
		if (channelInfo==null) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		map.put("channelInfo", channelInfo);
		
		// ??????????????????
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// ??????????????????
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);

		map.put("active", "portal.action?channelId="+channelId);
		
		return "portal/pc/template/" + templatePC + "/channel/" + channelInfo.getTemplate();
	}
	
	/**
	 * ?????????????????????
	 * @param videoId ????????????
	 * @return
	 */
	@RequestMapping("play.action")
	public String play(ModelMap map,
			HttpServletRequest request,
			@RequestParam(value="videoId") String videoId) {
		// ????????????
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");
		
		// ???????????????
		try {
			Integer.parseInt(videoId);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		// ????????????????????????
		VideoInfo videoInfo = videoInfoService.selectByIdWithPortal(videoId);
		if (videoInfo==null) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		if ("0".equals(videoInfo.getStatus())) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		map.put("videoInfo", videoInfo);
		
		// ???????????????seo
		SeoInfo seoInfo = seoInfoService.selectByType("play");
		map.put("seoInfo", seoInfo);
		
		// ??????????????????
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// ??????????????????
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// ?????????????????????????????????
		Map<String, Object> mediaInfo = null;
		try {
			mediaInfo = mediaInfoService.selectByMediaId(videoInfo.getMediaId());
			map.put("mediaInfo", mediaInfo);
		} catch (QingException e) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		
		// ??????????????????????????????
		TypeInfo typeInfo = typeInfoService.selectById(mediaInfo.get("type_id").toString());
		
		map.put("videoId", videoId);
		
		return "portal/pc/template/" + templatePC + "/play/" + typeInfo.getPlayTemplate();
	}
	
	/**
	 * ???????????????????????????
	 * @param videoId ????????????
	 * @return
	 */
	@RequestMapping("profile.action")
	public String profile(ModelMap map,
			HttpServletRequest request,
			@RequestParam(value="mediaId") String mediaId) {
		// ????????????
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");
		
		// ???????????????
		try {
			Integer.parseInt(mediaId);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		// ?????????????????????????????????
		Map<String, Object> mediaInfo = null;
		try {
			mediaInfo = mediaInfoService.selectByMediaId(mediaId);
			map.put("mediaInfo", mediaInfo);
		} catch (QingException e) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		
		// ????????????seo
		SeoInfo seoInfo = seoInfoService.selectByType("profile");
		map.put("seoInfo", seoInfo);
		
		// ??????????????????
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// ??????????????????
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// ??????????????????????????????
		TypeInfo typeInfo = typeInfoService.selectById(mediaInfo.get("type_id").toString());
		String profileTemplate = typeInfo.getProfileTemplate();
		if (StringUtils.isEmpty(profileTemplate)) {
			// 404?????????????????????????????????
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		
		map.put("mediaId", mediaId);
		
		return "portal/pc/template/" + templatePC + "/profile/" + profileTemplate;
	}
	
	/**
	 * ???????????????????????????
	 * @param videoId ????????????
	 * @return
	 */
	@RequestMapping("search.action")
	public String search(ModelMap map,
			HttpServletRequest request,
			@RequestParam(value="keyWord") String keyWord) {
		
		// ????????????
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// ??????????????????
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");
		
		// ??????????????????
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		map.put("keyWord", keyWord);
		
		return "portal/pc/template/" + templatePC + "/search/search";
	}
	
	/**
	 * ???????????????????????????
	 * @param videoId ????????????
	 * @return
	 */
	@RequestMapping("list.action")
	public String list(ModelMap map,
			HttpServletRequest request,
			@RequestParam(value="typeId") String typeId,
			@RequestParam(required=false, value="name") String fieldName,
			@RequestParam(required=false, value="value") String fieldValue) {
		// ????????????
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");
		
		// ???????????????
		try {
			Integer.parseInt(typeId);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		List<FieldInfo> list = fieldInfoService.getListField(typeId, fieldName, fieldValue);
		if (list==null) {
			// 404
			return "portal/pc/template/" + templatePC + "/error/404";
		}
		map.put("list", list);
		
		// ???????????????seo
		SeoInfo seoInfo = seoInfoService.selectByType("list");
		map.put("seoInfo", seoInfo);
		
		// ??????????????????
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// ??????????????????
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		map.put("typeId", typeId);
		map.put("fieldName", fieldName);
		map.put("fieldValue", fieldValue);
		
		return "portal/pc/template/" + templatePC + "/list/list";
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping("register.action")
	public String register(ModelMap map) {
		// ????????????
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");
		
		// ??????????????????
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		return "portal/pc/template/" + templatePC + "/user/register_page";
	}
	
	/**
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping("login.action")
	public String login() {
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");
		
		return "portal/pc/template/" + templatePC + "/user/login";
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping("login_page.action")
	public String loginPage(ModelMap map) {
		// ????????????
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");
		
		return "portal/pc/template/" + templatePC + "/user/login_page";
	}
	
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("find_pwd.action")
	public String findPwd(ModelMap map) {
		// ????????????
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// ??????????????????
		String templatePC = templateInfoService.selectNameByType("pc");
		
		return "portal/pc/template/" + templatePC + "/user/find_pwd";
	}
}
