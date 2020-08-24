package org.example;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Process.class)
public class MagicMojaProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Magic.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 애노테이션 프로세서는 라운드 개념으로 동작한다.
     * 각 라운드마다 애노테이션 프로세서가 처리할 작업을 찾으면 처리하고, 다음 으로 넘어간다.
     * 마치 filter chain과 비슷하다.
     *
     * 프로세서에서 true 를 리턴할 경우 Magic 애노테이션을 처리하는 경우이다.
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Magic.class);
        for (Element element : elements) {
            if (element.getKind() != ElementKind.INTERFACE) {
                // Interface 가 아닌 곳에 사용할 경우 에러를 내뱉는다.
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Magic annotation can not be use");
            } else {
                Name elementName = element.getSimpleName();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing" + elementName);
            }
        }
        return true;
    }
}
