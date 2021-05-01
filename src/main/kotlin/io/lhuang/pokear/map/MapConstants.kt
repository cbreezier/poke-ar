package io.lhuang.pokear.map

// The zoom level of a single logical tile
const val TILE_ZOOM_LEVEL = 15
// The width in world coordinates of a single logical tile
const val TILE_WORLD_WIDTH = 256.0 / (1 shl TILE_ZOOM_LEVEL)
// The actual number of pixels that we expect to find in each tile. The official size of a single tile is 256x256
// from Google Maps, but we retrieve a 512x512 image at a zoom level of 1 higher, so that we cover the same amount
// of world space in our tile, but at double the resolution.
const val TILE_PIXEL_WIDTH = 512