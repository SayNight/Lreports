package com.lls.report.util;

import java.util.Map;
import java.util.regex.Matcher;

import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.TransformationConfig;

public class DataFmtUtil {
	
	public static Object fmt(String strValue, Map<String, Object> context, JexlExpressionEvaluator evaluator){
	    Object evaluationResult = null;
        StringBuffer sb = new StringBuffer();
        TransformationConfig transformationConfig = new TransformationConfig();
        int beginExpressionLength = transformationConfig.getExpressionNotationBegin().length();
        int endExpressionLength = transformationConfig.getExpressionNotationEnd().length();
        Matcher exprMatcher = transformationConfig.getExpressionNotationPattern().matcher(strValue);
//        ExpressionEvaluator evaluator = getExpressionEvaluator();
        String matchedString;
        String expression;
        Object lastMatchEvalResult = null;
        int matchCount = 0;
        int endOffset = 0;
        while(exprMatcher.find()){
            endOffset = exprMatcher.end();
            matchCount++;
            matchedString = exprMatcher.group();
            expression = matchedString.substring(beginExpressionLength, matchedString.length() - endExpressionLength);
            lastMatchEvalResult = evaluator.evaluate(expression, context);
            exprMatcher.appendReplacement(sb, Matcher.quoteReplacement( lastMatchEvalResult != null ? lastMatchEvalResult.toString() : "" ));
        }
        String lastStringResult = lastMatchEvalResult != null ? lastMatchEvalResult.toString() : "";
        boolean isAppendTail = matchCount == 1 && endOffset < strValue.length();
        if( matchCount > 1 || isAppendTail){
            exprMatcher.appendTail(sb);
            evaluationResult = sb.toString();
        }else if(matchCount == 1){
            if(sb.length() > lastStringResult.length()){
                evaluationResult = sb.toString();
            }else {
                evaluationResult = lastMatchEvalResult;
            }
        }else if(matchCount == 0){
            evaluationResult = strValue;
        }
        return evaluationResult;
	}
	
}
