/**
 * Created by Vasilina on 06.07.2015.
 */
//EditableRegistry = {};
EditableRegistry = (function () {

        var wrappers = [];
        var availableClasses =
        {
            UserInfoKeyValueEntity: {
                fields: {
                    bannerTitle1: {
                        type: 'text',
                        title: 'Введите ваше преимущество',
                        defaultValue: 'Личный'
                    },
                    bannerTitle2: {
                        type: 'text',
                        title: 'Введите ваше преимущество',
                        defaultValue: 'подход к каждому'
                    },
                    bannerTitle3: {
                        type: 'text',
                        title: 'Введите описание вашего преимущества',
                        defaultValue: 'Описание преимущества'
                    }
                    /*  pk: {
                     values: ["id", "key"]
                     },
                     fields: {
                     value: {
                     type: 'text',
                     title: 'Введите описание вашего преимущества',
                     defaultValue: 'Описание бонуса'
                     }
                     }*/
                }
            },
            BonusEntity: {
                fields: {
                    title: {
                        type: 'text',
                        title: 'Введите ваше преимущество',
                        defaultValue: 'Преимущество'
                    },
                    description: {
                        type: 'text',
                        title: 'Введите описание вашего преимущества',
                        defaultValue: 'Описание бонуса'
                    }
                }
            },
            PropertyForListKeyValueEntity: {
                fields: {
                    education: {
                        type: 'text',
                        title: "Образование(университет, специальность)",
                        defaultValue: "БГПУ, психолог",
                        order: 1
                    },
                    direction_work: {
                        type: 'text',
                        title: "Основное направление работы(н-р, гештальт, психоанализ)",
                        defaultValue: "Гештальт",
                        order: 0
                    },
                    specialities: {
                        type: 'text',
                        title: "Основная аудитория(н-р, семейная, детская, кризисная)",
                        defaultValue: "Семейная психология, кризисная",
                        order: 1
                    },
                    experience: {
                        type: 'text',
                        title: "Опыт работы(в годах)",
                        defaultValue: "4 года",
                        order: 2
                    },
                    address: {
                        type: 'text',
                        title: "Адрес(город, улица, где вы принимаете)",
                        defaultValue: "ул. Кедышко, 5, офис 4",
                        order: 3
                    },
                    working_hours: {
                        type: 'text',
                        title: "Время работы(н-р, Понедельник-Пятница 9.00-18.00)",
                        defaultValue: "Понедельник-Пятница 9.00-18.00",
                        order: 4
                    },
                    previous_work: {
                        type: 'text',
                        title: "Предыдущие места работы",
                        defaultValue: "МЧС, школа",
                        order: 5
                    },
                    academic_degree: {
                        type: 'text',
                        title: "Ученая степень, если есть, в какой науке",
                        defaultValue: "Кандидат наук по психологии",
                        order: 6
                    }
                }
            },
            AuthorPropertyForListKeyValueEntity: {
                fields: {
                    education: {
                        type: 'text',
                        title: "Образование",
                        defaultValue: "БГПУ, психолог",
                        order: 1
                    },
                    direction_work: {
                        type: 'text',
                        title: "Основное направление работы",
                        defaultValue: "Гештальт",
                        order: 0
                    },
                    specialities: {
                        type: 'text',
                        title: "Основная аудитория",
                        defaultValue: "Семейная психология, кризисная",
                        order: 1
                    },
                    experience: {
                        type: 'text',
                        title: "Опыт работы",
                        defaultValue: "4 года",
                        order: 2
                    },
                    address: {
                        type: 'text',
                        title: "Адрес",
                        defaultValue: "ул. Кедышко, 5, офис 4",
                        order: 3
                    },
                    working_hours: {
                        type: 'text',
                        title: "Время работы",
                        defaultValue: "Понедельник-Пятница 9.00-18.00",
                        order: 4
                    },
                    previous_work: {
                        type: 'text',
                        title: "Предыдущие места работы",
                        defaultValue: "МЧС, школа",
                        order: 5
                    },
                    academic_degree: {
                        type: 'text',
                        title: "Ученая степень",
                        defaultValue: "Кандидат наук по психологии",
                        order: 6
                    }
                }
            }
        };

        function init() {
            for (var key in availableClasses) {
                alert("className : " + key);
                var fieldsInfo = "Fields info:\r\n";
                var availableFields = availableClasses[key]['fields'];
                for (var fieldKey in availableFields) {
                    fieldsInfo += fieldKey + " : " + availableFields[fieldKey]['title'] + " of " + availableFields[fieldKey]['type'];
                    fieldsInfo += "\r\n";
                }
                alert(fieldsInfo);
            }
        }

        var _getUrl = function (className) {
            return (className == 'BonusEntity' ? EditableRegistry.DEFAULT_URL : EditableRegistry.PSY_URL);
        };

        return function (config) {
            //init();
            this.addEditable = function (element, entityId, className, fieldName, value) {
                if (angular.isDefined(availableClasses[className]) && angular.isDefined(availableClasses[className]['fields'][fieldName])) {
                    var fieldInfo = availableClasses[className]['fields'][fieldName];

                    if (entityId === undefined) {
                        entityId = '';
                    }
                    if (entityId === null) {

                    }
                    if (!value) {
                        element.empty();
                    }

                    element.editable(
                        {
                            "title": fieldInfo['title'],
                            "type": fieldInfo['type'],
                            "pk": entityId,
                            "params": function (params) {
                                params.className = className;
                                return params;
                            },
                            "mode": EditableRegistry.DEFAULT_MODE,
                            "name": fieldName,
                            "url": _getUrl(className),
                            "value": value,
                            "defaultValue": fieldInfo['defaultValue'],
                            "emptytext": fieldInfo['defaultValue']
                        }
                    );
                } else {
                    console.log("cannot find class " + className + " field " + fieldName);
                }
            };
            this.getFieldsForClass = function (className) {
                if (angular.isDefined(availableClasses[className])) {
                    return availableClasses[className];
                } else {
                    return null;
                }
            }

        }
    })();
angular = window.angular;
//var editableRegistry = new EditableRegistry(angular);
//editableRegistry.addEditable(null, "BonusEntity", "title");
EditableRegistry.DEFAULT_URL = "/rest/psy/data/saveBonus";
EditableRegistry.PSY_URL = "/rest/psy/data/savePsyCharacteristics";
EditableRegistry.DEFAULT_MODE = "popup";
//EditableRegistry.SEPARATOR = "--";


