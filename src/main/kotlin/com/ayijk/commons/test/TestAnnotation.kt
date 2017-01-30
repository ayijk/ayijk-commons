package com.ayijk.commons.test

import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic.Kind.WARNING


/**
 * Created by ayijk on 2017/01/29.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION,
    AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class TestDone

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION,
    AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class TestYet


class TestYetProcessor : AbstractProcessor() {
  override fun getSupportedSourceVersion(): SourceVersion {
    return SourceVersion.latestSupported()
  }

  override fun getSupportedAnnotationTypes(): Set<String> {
    return Collections.singleton(TestYet::class.java.name)
  }

  override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
    val elements = roundEnv.getElementsAnnotatedWith(TestYet::class.java)

    for (element in elements) {
      //processingEnv.messager.printMessage(WARNING, "@Warning: " + element.getAnnotation(TestYet::class.java).value, element)
      processingEnv.messager.printMessage(WARNING, "@Warning: " + element.getAnnotation(TestYet::class.java).javaClass, element)
    }

    return true
  }
}