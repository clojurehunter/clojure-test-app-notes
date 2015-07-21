(ns notes.views
  (:require

   ; "Шаблонизатор"
   [selmer.parser :as parser]
   [selmer.filters :as filters]

   ; Время и дата
   [joda-time :as t]

   ; Для HTTP заголовков
   [ring.util.response :refer [content-type response]]

   ; Для CSRF защиты
   [ring.util.anti-forgery :refer [anti-forgery-field]]))

; Подскажем Selmer где искать наши шаблоны
(parser/set-resource-path! (clojure.java.io/resource "templates"))

; Чтобы привести дату в человеко-понятный формат
(defn format-date-and-time
  "Отформатировать дату и время"
  [date]
  (let [formatter (t/formatter "yyyy-MM-dd в H:m:s" :date-time)]
    (when date
      (t/print formatter date))))

; Добавим фильтр для использования в шаблоне
(filters/add-filter! :format-datetime
                     (fn [content]
                       [:safe (format-date-and-time content)]))

; Добавим тэг с полем для форм в нем будет находится
; автоматически сгенерированное поле с anti-forgery ключом
(parser/add-tag! :csrf-field (fn [_ _] (anti-forgery-field)))

(defn render [template & [params]]
  "Эта функция будет отображать наши html шаблоны
  и передавать в них данные"
  (-> template
      (parser/render-file

        ; Добавим к получаемым данным постоянные
        ; значения которые хотели бы получать
        ; на любой странице
        (assoc params
          :title "Менеджер заметок"
          :page (str template)))

      ; Из всего этого сделаем HTTP ответ
      response
      (content-type "text/html; charset=utf-8")))

(defn note
  "Страница просмотра заметки"
  [note]
  (render "note.html"

          ; Передаем данные в шаблон
          {:note note}))

(defn edit
  "Страница редактирования заметки"
  [note]
  (render "edit.html"

          ; Передаем данные в шаблон
          {:note note}))

(defn create
  "Страница создания заметки"
  []
  (render "create.html"))

(defn index
  "Главная страница приложения. Список заметок"
  [notes]
  (render "index.html"

          ; Передаем данные в шаблон
          ; Если notes пуст вернуть false
          {:notes (if (not-empty notes)

                    ; Давайте перевернем наши заметки
                    ; чтобы вверху были самые свежие из них
                    (reverse notes)
                    false)}))
