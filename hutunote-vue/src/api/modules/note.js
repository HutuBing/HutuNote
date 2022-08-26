/*
 * @Description: note
 * @Autor: liaojb
 * @Date: 2022-08-11 10:40:53
 * @LastEditors: liaojb
 * @LastEditTime: 2022-08-22 16:39:16
 */
import qs from 'qs'
import $http from '@/utils/http'
import { base_url } from '@/utils/config.js'

/**
 * @description: 笔记列表
 * @Date: 2022-08-11 10:43:15
 * @author: liaojb
 */
export const list = (params) => $http.get(`${base_url}/note/list?${qs.stringify(params)}`)

/**
 * @description: ID查笔记内容
 * @Date: 2022-08-25 22:26:15
 * @author: liaojb
 */
export const getById = (id) => $http.get(`${base_url}/note/${id}`)

/**
 * @description: 修改笔记内容
 * @Date: 2022-08-25 23:30:15
 * @author: liaojb
 */
export const updateById = (params) => $http.put(`${base_url}/note/${params.id}`, params)

/**
 * @description: 新建笔记
 * @Date: 2022-08-26 10:38:15
 * @author: liaojb
 */
export const save = (params) => $http.post(`${base_url}/note`, params)