package com.plugin.project.language;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.plugin.project.language.psi.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CMinusUtil {

    public static List<PsiElement> findReferences(Project project, String id) {
        List<PsiElement> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<PsiElement> references = PsiTreeUtil.findChildrenOfType(cMinusFile, PsiElement.class);
                for (PsiElement reference : references) {
                    String referenceId = "";
                    if (reference instanceof CMinusFunDeclaration) {
                        referenceId = ((CMinusFunDeclaration) reference).getFunDeclId();
                    } else if (reference instanceof CMinusVarDeclaration) {
                        referenceId = ((CMinusVarDeclaration) reference).getVarDeclId();
                    } else if (reference instanceof CMinusConstDeclaration) {
                        referenceId = ((CMinusConstDeclaration) reference).getConstDeclId();
                    }

                    if (!referenceId.isEmpty()) {
                        if (id.split("\\(")[0].equals(referenceId)) {
                            result.add(reference);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<CMinusFunDeclaration> findFunctionReferences(Project project, String id) {
        List<CMinusFunDeclaration> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<CMinusFunDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusFunDeclaration.class);
                for (CMinusFunDeclaration reference : references) {
                    if (id.split("\\(\\)")[0].equals(reference.getFunDeclId())) {
                        result.add(reference);
                    }
                }
            }
        }
        return result;
    }

    public static List<CMinusVarDeclaration> findVariableReferences(Project project, String id) {
        List<CMinusVarDeclaration> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<CMinusVarDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusVarDeclaration.class);
                for (CMinusVarDeclaration reference : references) {
                    if (id.equals(reference.getVarDeclId())) {
                        result.add(reference);
                    }
                }
            }
        }
        return result;
    }

    public static List<CMinusConstDeclaration> findConstantReferences(Project project, String id) {
        List<CMinusConstDeclaration> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<CMinusConstDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusConstDeclaration.class);
                for (CMinusConstDeclaration reference : references) {
                    if (id.equals(reference.getConstDeclId())) {
                        result.add(reference);
                    }
                }
            }
        }
        return result;
    }

    public static List<PsiElement> findReferences(Project project) {
        List<PsiElement> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<PsiElement> references = PsiTreeUtil.findChildrenOfType(cMinusFile, PsiElement.class);
                if (references != null) {
                    Collections.addAll(result, references.toArray(new PsiElement[references.size()]));
                }
            }
        }
        return result;
    }

    public static List<CMinusFunDeclaration> findLocalFunctionReferences(PsiFile containingFile, String id) {
        List<CMinusFunDeclaration> result = new ArrayList<>();
        if (containingFile instanceof CMinusFile && containingFile != null) {
            CMinusFile cMinusFile = (CMinusFile) containingFile;
            Collection<CMinusFunDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusFunDeclaration.class);
            for (CMinusFunDeclaration reference : references) {
                if (id.split("\\(")[0].equals(reference.getFunDeclId())) {
                    result.add(reference);
                }
            }
        }

        return result;
    }

    public static List<CMinusVarDeclaration> findLocalVariableReferences(PsiFile containingFile, String id) {
        List<CMinusVarDeclaration> result = new ArrayList<>();
        if (containingFile instanceof CMinusFile && containingFile != null) {
            CMinusFile cMinusFile = (CMinusFile) containingFile;
            Collection<CMinusVarDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusVarDeclaration.class);
            for (CMinusVarDeclaration reference : references) {
                if (id.equals(reference.getVarDeclId())) {
                    result.add(reference);
                }
            }
        }

        return result;
    }

    public static List<CMinusConstDeclaration> findLocalConstantReferences(PsiFile containingFile, String id) {
        List<CMinusConstDeclaration> result = new ArrayList<>();
        if (containingFile instanceof CMinusFile && containingFile != null) {
            CMinusFile cMinusFile = (CMinusFile) containingFile;
            Collection<CMinusConstDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusConstDeclaration.class);
            for (CMinusConstDeclaration reference : references) {
                if (id.equals(reference.getConstDeclId())) {
                    result.add(reference);
                }
            }
        }

        return result;
    }

    public static List<CMinusParam> findLocalParamReferences(PsiFile containingFile, String id) {
        List<CMinusParam> result = new ArrayList<>();
        if (containingFile instanceof CMinusFile && containingFile != null) {
            CMinusFile cMinusFile = (CMinusFile) containingFile;
            Collection<CMinusParam> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusParam.class);
            for (CMinusParam reference : references) {
                if (id.equals(reference.getParamId())) {
                    result.add(reference);
                }
            }
        }

        return result;
    }

    public static List<PsiElement> findLocalReferences(PsiFile containingFile, String id) {
        List<PsiElement> result = new ArrayList<>();
        if (containingFile instanceof CMinusFile) {
            CMinusFile cMinusFile = (CMinusFile) containingFile;
            Collection<PsiElement> references = PsiTreeUtil.findChildrenOfType(cMinusFile, PsiElement.class);
            for (PsiElement reference : references) {
                String referenceId = "";
                if (reference instanceof CMinusFunDeclaration) {
                    referenceId = ((CMinusFunDeclaration) reference).getFunDeclId();
                } else if (reference instanceof CMinusVarDeclaration) {
                    referenceId = ((CMinusVarDeclaration) reference).getVarDeclId();
                } else if (reference instanceof CMinusConstDeclaration) {
                    referenceId = ((CMinusConstDeclaration) reference).getConstDeclId();
                } else if (reference instanceof CMinusParam) {
                    referenceId = ((CMinusParam) reference).getParamId();
                }

                if (!referenceId.isEmpty()) {
                    if (id.split("\\(")[0].replaceAll("\\s","").equals(referenceId)) {
                        result.add(reference);
                    }
                }
            }
        }

        return result;
    }

    public static List<PsiElement> findLocalReferences(PsiFile containingFile) {
        List<PsiElement> result = new ArrayList<>();
        if (containingFile instanceof CMinusFile) {
            CMinusFile cMinusFile = (CMinusFile) containingFile;
            Collection<PsiElement> references = PsiTreeUtil.findChildrenOfType(cMinusFile, PsiElement.class);
            if (references != null) {
                Collections.addAll(result, references.toArray(new PsiElement[references.size()]));
            }
        }

        return result;
    }

    public static List<CMinusCall> findLocalFunctionCalls(PsiFile containingFile, String id) {
        List<CMinusCall> result = new ArrayList<>();
        if (containingFile instanceof CMinusFile && containingFile != null) {
            CMinusFile cMinusFile = (CMinusFile) containingFile;
            Collection<CMinusCall> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusCall.class);
            for (CMinusCall reference : references) {
                if (id.split("\\(")[0].equals(reference.getCallId())) {
                    result.add(reference);
                }
            }
        }

        return result;
    }


    public static List<CMinusVar> findLocalVarUsages(PsiFile containingFile, String id) {
        List<CMinusVar> result = new ArrayList<>();
        if (containingFile instanceof CMinusFile && containingFile != null) {
            CMinusFile cMinusFile = (CMinusFile) containingFile;
            Collection<CMinusVar> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusVar.class);
            for (CMinusVar reference : references) {
                if (id.equals(reference.getVarId())) {
                    result.add(reference);
                }
            }
        }

        return result;
    }
}
