
package youten.redo.feelbadsend.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    /**
     * SimpleDateFormatマクロ込み文字列を評価する <br />
     * 例）<br />
     * valueSDF("今日は$yyyy-MM-dd$です", "$") → return "今日は2011-04-30です"<br />
     *
     * @param target マクロ評価対象文字列
     * @param delimiter デリミタ文字（例："$"）
     * @return 評価済み文字列、delimiterが不正、フォーマット不正等の失敗時にはnullを返す。
     */
    public static String valueSDF(String target, String delimiter) {
        // parameter check
        if ((target == null) || (delimiter == null)) {
            return null;
        }
        if ("".equals(delimiter)) {
            return null;
        }

        String ret = target;

        while (ret.indexOf(delimiter) != -1) {
            int startIndex = ret.indexOf(delimiter);
            int endIndex = ret.indexOf(delimiter, startIndex + delimiter.length());
            if ((startIndex == -1) || (endIndex == -1)) {
                break;
            }
            String format = ret.substring(startIndex + delimiter.length(), endIndex);
            SimpleDateFormat sdf = null;
            if (format != null) {
                try {
                    sdf = new SimpleDateFormat(format);
                } catch (IllegalArgumentException iae) {
                    // iae.printStackTrace();
                }
            }
            if (sdf == null) {
                // フォーマット不正時にはエラーで終わる。
                return null;
            }
            Date now = new Date();
            String replaceDate = sdf.format(now);
            if (replaceDate == null) {
                // 変換失敗時にもエラーで終わる。
                return null;
            }
            ret = ret
                    .replace(ret.substring(startIndex, endIndex + delimiter.length()), replaceDate);
        }

        return ret;
    }

    /**
     * @param csvString
     * @return 成功時String配列 失敗時null
     */
    public static String[] parseCSV(String csvString) {
        String[] ret = null;
        if (csvString != null) {
            ret = csvString.split(",", 0);
        }
        return ret;
    }
}
