# react-native-cross-actionsheet

Simple to use, cross platform ActionSheets using **Native** Android ActionSheets and ActionSheetIOS.

As it uses Native components, it can be called statically, and no JSX components or show/hide state management are required. Just import the library and you're good to go.

## Quickstart

yarn: `yarn add react-native-cross-actionsheet`

npm: `npm install react-native-cross-actionsheet`

```typescript
import { ActionSheet } from 'react-native-cross-actionsheet'

ActionSheet.options({
  options: [
    { text: 'Create', onPress: () => console.log('create') },
    { text: 'Update', onPress: () => console.log('update') },
    { text: 'Delete', destructive: true, onPress: () => console.log('delete')}
  ],
  cancel: { onPress: () => console.log('cancel') }
})
```

## Preview

**Android**

<img src="https://i.imgur.com/HSPgkCw.gif"/>

**iOS (uses ActionSheetIOS)**

<img src="https://i.imgur.com/XJ6rgw5.gif"/>

# Features

- Native Android ActionSheets
- Feature parity with iOS
- Modern sleek UI
- Static calling, no JSX components required
- Typescript support
- Async support

# Usage

## ActionSheet.options

It's recommended to use the `ActionSheet.options` API as it is cleaner, more straightforward to use, and allows `awaiting`.

| Name            | Type                              | Required | Default   |
| ----------------| ----------------------------------| -------- | --------- |
| title           | string                            | No       |           |
| message         | string                            | No       |           |
| options         | { text, onPress, destructable }   | Yes      |           |
|   .text         | string                            | Yes      |           |
|   .onPress      | () => void                        | No       |           |
|   .destructable | boolean                           | No       | false     |
| cancel          | false OR { text, onPress }        | Yes      |           |
|   .text         | string                            | No       | 'Cancel'  |
|   .onPress      | () => void                        | No       |           |
| tintColor       | string (eg. '#0088FF')            | No       |           |
| anchor (iOS)    | number                            | No       |           |

### Simple
```typescript
import { ActionSheet } from 'react-native-cross-actionsheet'
ActionSheet.options({
  options: [
    { text: 'Create', onPress: () => console.log('create') },
    { text: 'Update', onPress: () => console.log('update') },
    { text: 'Delete', destructive: true, onPress: () => console.log('delete')}
  ],
  cancel: { onPress: () => console.log('cancel') }
})
```

### Additional Options
```typescript
import { ActionSheet } from 'react-native-cross-actionsheet'
ActionSheet.options({
    title: 'ActionSheet Title',
    message: 'Select an option',
    options: [
      { text: 'Create', onPress: () => console.log('create') },
      { text: 'Update', onPress: () => console.log('update') },
      { text: 'Delete', onPress: () => console.log('delete'), destructive: true }
    ],
    cancel: { text: 'Cancel', onPress: () => console.log('cancel') },
    tintColor: '#008888'
})
```

### Disable Cancel
```typescript
import { ActionSheet } from 'react-native-cross-actionsheet'
ActionSheet.options({
    options: [
      { text: 'Create', onPress: () => console.log('create') },
      { text: 'Update', onPress: () => console.log('update') },
      { text: 'Delete', onPress: () => console.log('delete'), destructive: true }
    ],
    cancel: false
})
```


## ActionSheet.showActionSheetWithOptions

If you wish to stick with the traditional API, you can call `ActionSheet.showActionSheetWithOptions`, which uses the exact same API as [ActionSheetIOS](https://reactnative.dev/docs/actionsheetios).

`anchor` is only used for iOS.

### Simple
```typescript
import { ActionSheet } from 'react-native-cross-actionsheet'
ActionSheet.showActionSheetWithOptions(
  { 
    options: ['Create', 'Edit', 'Delete', 'Cancel'] 
  },
  buttonIndex => {
    console.log('buttonIndex', buttonIndex)
  }
)
```

### Additional Options
```typescript
import { ActionSheet } from 'react-native-cross-actionsheet'
ActionSheet.showActionSheetWithOptions(
  {
    title: 'Action Sheet',
    message: 'Choose an option',
    options: ['Create', 'Edit', 'Delete', 'Cancel'],
    destructiveButtonIndex: 2,
    cancelButtonIndex: 3,
    tintColor: '#008888'
  },
  buttonIndex => {
    console.log('buttonIndex', buttonIndex)
  }
)
```

# Only require usage of ActionSheetAndroid

If you only wish to import `ActionSheetAndroid` as you wish to handle ActionSheets differently for different platforms, then you may import it directly:

```typescript
import { ActionSheetAndroid } from 'react-native-cross-actionsheet'

ActionSheetAndroid.showActionSheetWithOptions({
... // same as AndroidSheetIOS
})
```

# Why Native?

You may be wondering, why do you need a native implementation when the JS implementation can also do the same job?

JS implementations require you to include the `<ActionSheet/>` component somewhere in your code. As this is a native implementation and not rendered on the React level, no JSX components are required. Just call the ActionSheet statically.

For JS implementations, ActionSheets are rendered at the same level as your Modal. In some cases where Modals are not properly written, this may cause a conflict when you attempt to render an ActionSheet on top of a Modal. As this uses a native Android implementation, it will always render on top of your React layer.
