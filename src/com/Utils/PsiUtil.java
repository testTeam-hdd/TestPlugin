package com.Utils;

import com.exception.PluginErrorMsg;
import com.exception.PluginRunTimeException;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.EverythingGlobalScope;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.tasks.GenerateTestScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * dongdong Created by 下午5:41  2018/7/10
 */
public class PsiUtil {
    //获取PsiClass对象
    public static PsiClass getPsiClass(Project project, String className) {
        PsiClass psiClass;
        String packageName = null;
        if (PsiUtil.isAllPackageName(className)) {
            packageName = className;
        }
        className = isGeneric(className)?getGenericType(className):className;

        if (EmptyUtils.isEmpty(packageName)) {
            PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, className + ".class", GlobalSearchScope.allScope(project));
            if (psiFiles.length == 0) {
                psiFiles = FilenameIndex.getFilesByName(project, className + ".java", GlobalSearchScope.allScope(project));
            }
            if (psiFiles.length == 0) {
                throw new PluginRunTimeException(PluginErrorMsg.CLASS_NOT_FIND);
            } else {
                PsiJavaFile psiJavaFile = (PsiJavaFile) psiFiles[0];
                psiJavaFile.getVirtualFile();
                packageName = psiJavaFile.getPackageName() + "." + className;
            }
        }
        PsiClass[] psiClasses = JavaPsiFacade.getInstance(project).findClasses(packageName, new EverythingGlobalScope(project));
        if (psiClasses.length == 0) {
            throw new PluginRunTimeException(PluginErrorMsg.PSICLASS_NOT_FIND);
        }
        psiClass = psiClasses[0];

        return psiClass;
    }

    //获取PsiJavaFile
    public static PsiJavaFile getPsiJavaFile(Project project, String className) {
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, className + ".class", GlobalSearchScope.allScope(project));
        if (psiFiles.length == 0) {
            psiFiles = FilenameIndex.getFilesByName(project, className + ".java", GlobalSearchScope.allScope(project));
        }
        if (psiFiles.length == 0) {
            throw new PluginRunTimeException(PluginErrorMsg.CLASS_NOT_FIND);
        }
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFiles[0];
        String packageName = psiJavaFile.getPackageName();
        return psiJavaFile;
    }

    //获取指定名称的文件
    public static PsiFile getPsiFile(Project project, String fileName) {
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.allScope(project));
        PsiFile psiFile = psiFiles[0];
        return psiFile;
    }

    //获取包名
    public static String getPackageName(Project project, String className) {
        String packageName = null;
        className = isGeneric(className)?getGenericType(className):className;
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, className + ".class", GlobalSearchScope.allScope(project));
        if (psiFiles.length == 0) {
            psiFiles = FilenameIndex.getFilesByName(project, className + ".java", GlobalSearchScope.allScope(project));
        }
        if (psiFiles.length == 0) {
            throw new PluginRunTimeException(PluginErrorMsg.CLASS_NOT_FIND);
        }
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFiles[0];
        packageName = psiJavaFile.getPackageName();
        return packageName;
    }

    //获取Psitype
    public static PsiType getPsiType(Project project, String className) {
        PsiType psiType = PsiType.getTypeByName(className, project, GlobalSearchScope.EMPTY_SCOPE);
        return psiType;
    }

    //获取表名
    public static String getTableName(Project project, String className) {
        String tableName = null;
        PsiType psiType = PsiUtil.getPsiType(project, className);
        PsiAnnotation[] a = psiType.getAnnotations();
//        String psi1 = psiType.getAnnotations()[0].getQualifiedName();
        for (PsiAnnotation psiAnnotation : psiType.getAnnotations()) {
            PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = psiAnnotation.getNameReferenceElement();
            PsiAnnotationOwner psiAnnotationOwner = psiAnnotation.getOwner();
            String psi = psiAnnotation.getQualifiedName();
            tableName = psi;
        }
        return tableName;
    }

    //获取方法参数
    public static List<PsiType> getMethodPrame(Project project, String className, String methodName) {
        PsiType type = null;
        String object = null;
        List<PsiType> list = new ArrayList<>();
        PsiClass psiClass = PsiUtil.getPsiClass(project, className);
        for (PsiMethod psiMethodName : psiClass.getAllMethods()) {
            if (psiMethodName.getName().equals(methodName)) {
                PsiParameterList psiParameterList = psiMethodName.getParameterList();
                for (PsiParameter psiParameter : psiMethodName.getParameterList().getParameters()) {
                    //获取类名
                    type = psiParameter.getTypeElement().getType();
                    list.add(type);
                }
                break;
            }
        }
        return list;
    }

    //获取返回类型
    public static PsiType getMethodRetureType(Project project, String className, String methodName) {
        PsiType type = null;
        PsiClass psiClass = PsiUtil.getPsiClass(project, className);
        for (PsiMethod psiMethodName : psiClass.getAllMethods()) {
            if (psiMethodName.getName().equals(methodName)) {
                type = psiMethodName.getReturnType();
                break;
            }
        }
        if (type == null) {
            throw new PluginRunTimeException(PluginErrorMsg.TEST_METHOD_NOT_FIND);
        }
        return type;
    }

    //判断是否为枚举类型
    public static boolean isEnum(PsiType psiType) {
        boolean isEnum = true;
        if (psiType.getPresentableText().equals("Object")) {
            return false;
        }
        if (Arrays.asList(GenerateTestScript.TYPE).contains(psiType.getPresentableText())) {
            return false;
        }
        PsiType superPsiType = psiType.getSuperTypes()[0].getDeepComponentType();
        if (!superPsiType.getPresentableText().contains("Enum")) {
            isEnum = false;
        }
        return isEnum;
    }


    //判断是否为集合类型
    public static boolean isCollection(PsiType psiType) {
        boolean isCollection = true;
        if (psiType.getPresentableText().equals("Object")) {
            return false;
        }
        if (Arrays.asList(GenerateTestScript.TYPE).contains(psiType.getPresentableText())) {
            return false;
        }
        PsiType superPsiType = psiType.getSuperTypes()[0].getDeepComponentType();
        if (!superPsiType.getPresentableText().contains("Collection")) {
            isCollection = false;
        }
        return isCollection;
    }

    //    public static String chooseCollection(String type) {
//        String collectionType = null;
//        if (type.contains("List")) {
//            collectionType = "java.util.List";
//        } else if (type.contains("Map")) {
//            collectionType = "java.util.Map";
//        } else if (type.contains("Set")) {
//            collectionType = "java.util.Set";
//        }
//        return collectionType;
//    }
    //获取一个枚举对象
    public static String getEnumObject(Project project, String className) {
        PsiClass psiClass = PsiUtil.getPsiClass(project, className);
        PsiField psiField = psiClass.getAllFields()[0];
        String enumName = psiField.getName();
        return enumName;
    }

    //判断是否为全包名
    public static boolean isAllPackageName(String packageName) {
        Boolean isAll = false;
        if (packageName.contains(".")) {
            isAll = true;
        }
        return isAll;
    }
    //截取全包名中的类名
    public static String subClassName(String className) {
        String packageName = className;
        if (className.contains(".")) {
            packageName = className.substring(className.lastIndexOf(".") + 1, className.length());

        }
        return packageName;
    }

    //判断类名是否是泛型
    public static boolean isGeneric(String className){
        boolean isGeneric = false;
        if (className.contains("<")){
            isGeneric = true;
        }
        return isGeneric;
    }

    //获取泛型类型
    public static String getGenericType(String className){
        if (className.contains("<")){
            className = className.substring(0,className.lastIndexOf("<"));
        }
        return className;
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
//      通过名称查找类名，自测失败\
//      PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(packageName+".TransfeeBo", GlobalSearchScope.allScope(project));
//通过名称查找类名