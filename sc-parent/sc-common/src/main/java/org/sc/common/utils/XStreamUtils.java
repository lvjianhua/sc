package org.sc.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * XStream工具
 * @author DongWenBin
 *
 */
public class XStreamUtils {

	private static final XmlFriendlyNameCoder XML_FRIENDLY_NAME_CODER = new XmlFriendlyNameCoder("_-", "_");

	private static final String ENCODING = "GBK";

	public static String toXML(Object obj) {
		XStream xs = createXStream();
		return xs.toXML(obj);
	}

	public static Object fromXML(String xml, String name, Class<?> clazz) {
		XStream xs = createXStream();
		xs.alias(name, clazz);
		return xs.fromXML(xml);
	}

	private static XStream createXStream() {
		XStream xs = new XStream(new DomDriver(ENCODING, XML_FRIENDLY_NAME_CODER)) {
			// 重写wrapMapper，防止因XML与对象字段数量不对应而抛出异常
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@Override
					public boolean shouldSerializeMember(Class definedIn, String fieldName) {
						if (definedIn == Object.class) {
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		xs.autodetectAnnotations(true);
		xs.setMode(XStream.NO_REFERENCES);
		xs.aliasSystemAttribute(null, "class");
		return xs;
	}

}
