import os
import javalang as java

from datetime import datetime

from javalang.tree import CompilationUnit
from javalang.tree import ClassDeclaration
from javalang.tree import FieldDeclaration
from javalang.tree import ReferenceType

def parse_file(filename: str):
    f = open(filename, 'r')
    fields = []
    staticFields = []
    className = ""
    for node in java.parse.parse(f.read()).types:
        className = node.name
        for element in node.body:
            if isinstance(element, FieldDeclaration):
                if "static" not in element.modifiers:
                    fields.append(parse_field(element))
                else:
                    staticFields.append(static_lower(element.declarators[0].name))
    return className, fields, staticFields

def parse_field(field: FieldDeclaration):
    # print(field)
    name_ = field.declarators[0].name
    type_ = parse_type(field.type)
    return type_, name_

def parse_type(type: ReferenceType):
    type_ = type.name
    if type.arguments is not None:
        type_ = type_ + "<" + ",".join(list(map(lambda x: parse_type(x.type), type.arguments))) + ">"
    return type_

def static_upper(name: str) -> str:
    return "".join(list(map(lambda x: x.upper() if x == x.lower() else "_" + x, name)))

def static_lower(name: str) -> str:
    i = 0
    lname = ""
    while i < len(name):
        if name[i] == "_":
            lname += name[i + 1]
            i += 2
        else:
            lname += name[i].lower()
            i += 1
    return lname


importStr = "bot.backend.nodes.events.utils.ClassField"

dirPath = "../src/main/java/bot/backend/nodes/events/"

for filePostfix in os.listdir(dirPath):
    if not filePostfix.endswith("Event.java"): continue

    fileName = dirPath + filePostfix

    name, fields, staticFields = parse_file(fileName)

    f = open(fileName, 'r')

    oldText = f.read()

    f.close()

    lines = oldText.split("\n")

    if lines[2] != f"import {importStr};":
        lines.insert(2, f"import {importStr};")

    is_changed_by_script = -1
    index = 0

    for line in lines:
        if line.startswith("\t// generated by script"):
            is_changed_by_script = index
            break
        index += 1


    today_date_time = datetime.now().strftime("%d/%m/%Y %H:%M:%S")

    if is_changed_by_script == -1:
        lines.insert(len(lines) - 2, "")
        lines.insert(len(lines) - 2, f"\t// generated by script at {today_date_time}")

    else:
        lines[is_changed_by_script] = f"\t// generated by script at {today_date_time}"


    for (field_type, field_name) in fields:
        if field_name in staticFields: continue

        upper_field_name = field_name[0].upper() + field_name[1:]

        lines.insert(len(lines) - 2, f"\tpublic static final ClassField<{name}, {field_type}> {static_upper(field_name)} = " + \
            f"new ClassField<>({name}::get{upper_field_name}, {name}::set{upper_field_name}, \"{field_name}\");")


    f = open(fileName, 'w')

    i = 0

    for line in lines:
        f.write(line)
        if (i < len(lines) - 1):
            f.write("\n")
        i += 1

    f.close()
