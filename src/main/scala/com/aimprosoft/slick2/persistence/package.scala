package com.aimprosoft.slick2

package object persistence {

  type Identifiable[ID] = {def id: Option[ID]}

}
