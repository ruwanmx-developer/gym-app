import pymysql
from app import app
from fastapi import FastAPI
from config import mysql
from flask import jsonify
from flask import flash, request

#prefix /users/
#Register a New User
@app.route('/users/reg-new-usr/', methods=['POST'])
def create_user():
    try:
        _json = request.json
        _email = _json['email']
        _password = _json['password']
        _first_name = _json['first_name']
        _last_name = _json['last_name']
        _gender = _json['gender']
        if _email and _password and _first_name and _last_name and _gender and request.method == 'POST':
            conn = mysql.connect()
            cursor = conn.cursor(pymysql.cursors.DictCursor)
            sqlQuery = "INSERT INTO users VALUES (NULL, %s, %s, %s, %s, %s, NULL, NULL, NULL)"
            bindData = (_email, _password, _first_name, _last_name, _gender)
            cursor.execute(sqlQuery, bindData)
            conn.commit()
            message = {
                'status': 200,
                'message': 'User registered successfully',
                'valid': True,
            }
            respone = jsonify(message)
            respone.status_code = 200
            return respone
        else:
            return showMessage()
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        conn.close()

#Update an Existing User
@app.route('/users/upd-ext-usr/', methods=['POST'])
def update_user():
    try:
        _json = request.json
        _id = _json['id']
        _age = _json['age']
        _height = _json['height']
        _weight = _json['weight']

        if _id and _age and _height and _weight and request.method == 'POST':
            conn = mysql.connect()
            cursor = conn.cursor(pymysql.cursors.DictCursor)
            sqlQuery = "UPDATE users SET age=%s, height=%s, weight=%s WHERE id=%s"
            bindData = (_age, _height, _weight, _id)
            cursor.execute(sqlQuery, bindData)
            conn.commit()
            message = {
                'status': 200,
                'message': 'User updated successfully',
                'valid': True,
            }
            respone = jsonify(message)
            respone.status_code = 200
            return respone
        else:
            return showMessage()
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        conn.close()

#Log an Exisiting User
@app.route('/users/log-ext-usr/', methods=['POST'])
def log_user():
    try:
        _json = request.json
        _email = _json['email']
        _password = _json['password']
        
        if _email and _password and  request.method == 'POST':
            conn = mysql.connect()
            cursor = conn.cursor(pymysql.cursors.DictCursor)
            sqlQuery = "SELECT * FROM users WHERE email=%s AND password=%s"
            bindData = (_email, _password)
            cursor.execute(sqlQuery, bindData)
            x = True if (cursor.rowcount == 1) else False
            result_set = cursor.fetchone()
            
            y = (result_set["gender"]) if (cursor.rowcount == 1) else "Invalid email or password"
            message = {
                'status': int(result_set["id"]) if (cursor.rowcount == 1) else 0,
                'message': y,
                'valid': x,
            }
            respone = jsonify(message)
            respone.status_code = 200
            return respone
        else:
            return showMessage()
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        conn.close()

#Get user details
@app.route('/users/get-ext-usr/', methods=['POST'])
def get_user():
    try:
        _json = request.json
        _id = _json['id']
        
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute("SELECT * FROM users WHERE id=%s", _id)
        empRow = cursor.fetchone()
        respone = jsonify(empRow)
        respone.status_code = 200
        return respone
       
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        conn.close()

#Get diet plan
@app.route('/users/get-diet-plan/', methods=['POST'])
def get_diet():
    try:
        _json = request.json
        _type = _json['type']
        _date = _json['date']
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)  
        sqlQuery = "SELECT * FROM diet_plan WHERE type=%s AND date=%s"
        bindData = (_type, _date)
        cursor.execute(sqlQuery, bindData)
        
        empRow = cursor.fetchone()
        respone = jsonify(empRow)
        respone.status_code = 200 
        return respone
       
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        conn.close()

#Get schedule
@app.route('/users/get-she-plan/', methods=['POST'])
def get_schedule():
    try:
        _json = request.json
        _type = _json['type']
        
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)  
        sqlQuery = "SELECT * FROM schedule_plan WHERE type=%s"
        bindData = (_type)
        cursor.execute(sqlQuery, bindData)
        
        empRow = cursor.fetchall()
        respone = jsonify(empRow)
        respone.status_code = 200 
        return respone
       
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        conn.close()

# Handle Error
@app.errorhandler(404)
def showMessage(error=None):
    message = {
        'status': 404,
        'message': 'Sever side error occured : ' + request.url,
    }
    respone = jsonify(message)
    respone.status_code = 404
    return respone


if __name__ == "__main__":
    ip = str(input("Enter device IP Address : "))
    app.run(host=ip)
