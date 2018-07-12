package com.Utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.search.EverythingGlobalScope;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

/**
 * dongdong Created by 下午5:41  2018/7/10
 */
public class PsiUtil {
    //获取PsiClass对象
    public static PsiClass getPsiClass(Project project, String className) {
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, className + ".class", GlobalSearchScope.allScope(project));
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFiles[0];
        String packageName = psiJavaFile.getPackageName();
//      通过名称查找类名，自测失败
//      PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(packageName+".TransfeeBo", GlobalSearchScope.allScope(project));
        //通过名称查找类名
        PsiClass[] psiClasses = JavaPsiFacade.getInstance(project).findClasses(packageName + "." + className, new EverythingGlobalScope(project));
        PsiClass psiClass = psiClasses[0];
        return psiClass;
    }

    //获取PsiJavaFile
    public static PsiJavaFile getPsiJavaFile(Project project, String className) {
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, className + ".class", GlobalSearchScope.allScope(project));
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFiles[0];
        String packageName = psiJavaFile.getPackageName();
        return psiJavaFile;
    }

    //获取包名
    public static String getPackageName(Project project, String className) {
        String packageName = null;
        try {
            PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, className + ".class", GlobalSearchScope.allScope(project));
            PsiJavaFile psiJavaFile = (PsiJavaFile) psiFiles[0];
            packageName = psiJavaFile.getPackageName();
        } catch (Exception e) {
            Messages.showInfoMessage(project, "类名错误，请检查后重新填写", "title");
        }
        return packageName;
    }
}
