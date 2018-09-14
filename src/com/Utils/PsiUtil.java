package com.Utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.search.EverythingGlobalScope;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.HashMap;
import java.util.Map;

/**
 * dongdong Created by 下午5:41  2018/7/10
 */
public class PsiUtil {
    //获取PsiClass对象
    public static PsiClass getPsiClass(Project project, String className) {
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, className + ".class", GlobalSearchScope.allScope(project));
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFiles[0];
        psiJavaFile.getVirtualFile();
        String packageName = psiJavaFile.getPackageName();
//      通过名称查找类名，自测失败
//      PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(packageName+".TransfeeBo", GlobalSearchScope.allScope(project));
        //通过名称查找类名
        PsiClass[] psiClasses = JavaPsiFacade.getInstance(project).findClasses(packageName + "." + className, new EverythingGlobalScope(project));
        PsiClass psiClass = psiClasses[0];
        String name = psiClass.getName();
        PsiField[] a = psiClass.getFields();
        for(PsiField psiField:a){

            PsiIdentifier psiIdentifier =psiField.getNameIdentifier();
           String b =  psiField.getName();
           int i =1;
        }
        for(PsiField psiField:psiClass.getAllFields()){
            String c = psiField.getName();
            int d = 0;
        }



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

    //获取方法参数
    public static Map<PsiType, String> getMethodPrame(Project project, String className, String methodName) {
        PsiType type = null;
        String object = null;
        Map<PsiType, String> map = new HashMap<>();
        PsiClass psiClass = PsiUtil.getPsiClass(project, className);
        for (PsiMethod psiMethodName : psiClass.getAllMethods()) {
            if (psiMethodName.getName().equals(methodName)) {
                for (PsiParameter psiParameter : psiMethodName.getParameterList().getParameters()) {
                    //获取类名
                    type = psiParameter.getTypeElement().getType();
                    object = psiParameter.getName();
                    map.put(type, object);
                }

            }
        }
        return map;
    }

    //获取返回类型
    public static PsiType getMethodRetureType(Project project, String className, String methodName) {
        PsiType type = null;
        PsiClass psiClass = PsiUtil.getPsiClass(project, className);
        for (PsiMethod psiMethodName : psiClass.getAllMethods()) {
            if (psiMethodName.getName().equals(methodName)) {
                type = psiMethodName.getReturnType();
                Class classa = type.getClass();
                int a = 1;
            }
        }
        return type;
    }

    //判断是否为枚举类型
    public static boolean isEnum(PsiType psiType) {
        boolean isEnum = true;
        PsiType superPsiType = psiType.getSuperTypes()[0].getDeepComponentType();
        if (!superPsiType.getPresentableText().contains("Enum")){
            isEnum = false;
        }
        return isEnum;
    }
    //判断是否为枚举类型
    public static boolean isCollection(PsiType psiType) {
        boolean isCollection = true;
        PsiType superPsiType = psiType.getSuperTypes()[0].getDeepComponentType();
        if (!superPsiType.getPresentableText().contains("Collection")){
            isCollection = false;
        }
        return isCollection;
    }
}
//获取类型的包路径
//        type = psiParameter.getTypeElement().getType().getCanonicalText().toString();
//       //获取类名
//       type = psiParameter.getTypeElement().getType().getPresentableText().toString();
//         //获取类型的包路径
//       type = psiParameter.getTypeElement().getType().getInternalCanonicalText().toString();
//获取包括父类中的所有属性值
//psiClass.getAllFields()
//获取当前类的所有属性
//psiClass.getFields()