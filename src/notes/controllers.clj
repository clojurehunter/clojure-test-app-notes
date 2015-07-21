(ns notes.controllers
  (:require

   ; Функция редиректа
   [ring.util.response :refer [redirect]]

   ; Функции для взаимодействия с БД
   [notes.db :as db]))

(defn delete
  "Контроллер удаления заметки"
  [id]
  (do
    (db/remove-note id)
    (redirect "/")))

(defn edit
  "Контроллер редактирования заметки"
  [request]

  ; Получаем данные из формы
  (let [note-id (get-in request [:form-params "id"])
        note {:title (get-in request [:form-params "title"])
              :text (get-in request [:form-params "text"])}]

    ; Проверим данные
    (if (and (not-empty (:title note))
             (not-empty (:text note)))

      ; Если все ОК
      ; обновляем документ в БД
      ; переносим пользователя
      ; на главную страницу
      (do
        (db/update-note note-id note)
        (redirect "/"))

      ; Если данные пусты тогда ошибка
      "Проверьте правильность введенных данных")))

(defn create
  "Контроллер создания заметки"
  [request]

  ; Получаем данные из формы
  ; не будем плодить переменные
  ; и сразу создадим hash-map
  ; (ассоциативный массив)
  (let [note {:title (get-in request [:form-params "title"])
              :text (get-in request [:form-params "text"])}]

    ; Проверим данные
    (if (and (not-empty (:title note))
             (not-empty (:text note)))

      ; Если все ОК
      ; добавляем их в БД
      ; перенесем пользователя
      ; на главную страницу
      (do
        (db/create-note note)
        (redirect "/"))

      ; Если данные пусты тогда ошибка
      "Проверьте правильность введенных данных")))
