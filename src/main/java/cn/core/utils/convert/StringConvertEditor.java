package cn.core.utils.convert;

import org.apache.commons.lang3.StringEscapeUtils;

import java.beans.PropertyEditorSupport;

/**
 * 字符串处理类 String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
 *
 * @author Administrator
 * @date
 */
public class StringConvertEditor extends PropertyEditorSupport {
    @Override
    public String getAsText() {
        Object value = getValue();
        return value == null ? "" : value.toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
        super.setAsText(text);
    }
}
