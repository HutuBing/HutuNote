/*
 * @Description: note
 * @Autor: liaojb
 * @Date: 2022-08-11 10:40:53
 * @LastEditors: liaojb
 * @LastEditTime: 2022-08-22 16:39:16
 */

import $http from '@/utils/http'
import { base_url } from '@/utils/config.js'

/**
 * @description: 笔记列表
 * @Date: 2022-08-11 10:43:15
 * @author: liaojb
 */
export const list = () => $http.get(`${base_url}/note/list`)