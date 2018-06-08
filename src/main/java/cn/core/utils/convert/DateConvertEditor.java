package cn.core.utils.convert;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.beans.PropertyEditorSupport;

/**
 * DateConvertEditor class
 *
 * @author Administrator
 * @date
 */
public class DateConvertEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isNoneEmpty(text)) {
            try {
                super.setValue(DateUtils.parseDate(text));
            } catch (Exception ex) {
                IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
                iae.initCause(ex);
                throw iae;
            }


        } else {
            super.setValue(null);
        }
    }
}
