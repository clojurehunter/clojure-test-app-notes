(defproject notes "0.1.0-SNAPSHOT"
  :description "Менеджер заметок"
  :min-lein-version "2.0.0"
  :dependencies [; Да-да, сам Clojure тоже подключаем
                 ; как зависимость
                 [org.clojure/clojure "1.6.0"]

                 ; Маршруты для GET и POST запросов
                 [compojure "1.3.1"]

                 ; Обертка (middleware) для наших
                 ; маршрутов
                 [ring/ring-defaults "0.1.5"]

                 ; Шаблонизатор
                 [selmer "0.8.2"]

                 ; Добавляем Monger
                 [com.novemberain/monger "2.0.1"]

                 ; Дата и время
                 [clojure.joda-time "0.6.0"]]

  ; Поскольку веб-сервер подключать мы будем в
  ; следующей статье, пока доверим это дело
  ; Ring'у в который включен свой веб-сервер Jetty
  :plugins [[lein-ring "0.8.13"]]

  ; При запуске приложения Ring будет
  ; использовать переменную app содержащую
  ; маршруты и все функции которые они содержат
  :ring {:handler notes.handler/app}
  :profiles {:dev
             {:dependencies
              [[javax.servlet/servlet-api "2.5"]
               [ring-mock "0.1.5"]]}})
