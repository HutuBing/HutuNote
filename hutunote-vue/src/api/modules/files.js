/*
 * @Description: files
 * @Autor: liaojb
 * @Date: 2022-09-07 15:40:53
 * @LastEditors: liaojb
 * @LastEditTime: 2022-09-07 15:40:53
 */
import qs from 'qs'
import $http from '@/utils/http'
import { base_url } from '@/utils/config.js'

/**
 * @description: 上传文件
 * @Date: 2022-09-07 15:40:53
 * @author: liaojb
 */
export const upload = (params) => $http.post(`${base_url}/files/upload`, params)
/**
 * @description: 上传文件,只需哟url
 * @Date: 2022-09-07 15:40:53
 * @author: liaojb
 */
export const uploadHt = `${base_url}/files/uploadHt`
/**
 * @description: 上传文件,只需哟url
 * @Date: 2022-09-07 15:40:53
 * @author: liaojb
 */
export const uploadTemplate = `${base_url}/files/uploadTemplate`
/**
 * @description: 上传文件,只需哟url
 * @Date: 2022-09-07 15:40:53
 * @author: liaojb
 */
export const uploadImage = `${base_url}/files/uploadImage`
/**
 * @description: 上传文件
 * @Date: 2022-09-07 15:40:53
 * @author: liaojb
 */
export const listHt = (params) => $http.post(`${base_url}/files/listHt`, params)
/**
 * @description: 上传文件
 * @Date: 2022-09-07 15:40:53
 * @author: liaojb
 */
export const getConfig = (params) => $http.post(`${base_url}/files/getConfig`, params)
/**
 * @description: 上传文件
 * @Date: 2022-09-07 15:40:53
 * @author: liaojb
 */
export const updateConfig = (params) => $http.post(`${base_url}/files/updateConfig`, params)